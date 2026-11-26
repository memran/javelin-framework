package io.javelin.http.netty;

import io.javelin.core.AppProfile;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.http.common.HttpTimeoutConfig;
import io.javelin.http.common.StaticAssetResolver;
import io.javelin.http.common.StaticCachePolicy;
import io.javelin.http.common.TrustedProxyResolver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class NettyHttpServer implements HttpServerAdapter {
    private static final int HEADER_SLACK = 8192;

    private final String host;
    private final int port;
    private final int maxRequestBytes;
    private final HttpKernel kernel;
    private final TrustedProxyResolver trustedProxyResolver;
    private final StaticAssetResolver staticAssetResolver;
    private final HttpTimeoutConfig timeouts;
    private final AppProfile profile;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    public NettyHttpServer(String host, int port, HttpKernel kernel) {
        this(host, port, kernel, List.of(), Path.of("public"), 1_048_576, AppProfile.PROD);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies) {
        this(host, port, kernel, trustedProxies, Path.of("public"), 1_048_576, AppProfile.PROD);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot) {
        this(host, port, kernel, trustedProxies, staticRoot, 1_048_576, AppProfile.PROD);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot, int maxRequestBytes) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, AppProfile.PROD);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                           int maxRequestBytes, AppProfile profile) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, profile, StaticCachePolicy.DEFAULT);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                           int maxRequestBytes, AppProfile profile, StaticCachePolicy cachePolicy) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, profile, cachePolicy,
                HttpTimeoutConfig.DEFAULT);
    }

    public NettyHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                           int maxRequestBytes, AppProfile profile, StaticCachePolicy cachePolicy,
                           HttpTimeoutConfig timeouts) {
        this.host = host;
        this.port = port;
        this.maxRequestBytes = maxRequestBytes;
        this.timeouts = timeouts;
        this.profile = profile;
        this.trustedProxyResolver = new TrustedProxyResolver(trustedProxies);
        this.staticAssetResolver = new StaticAssetResolver(staticRoot, cachePolicy);
        this.kernel = kernel.withStaticFallback(staticAssetResolver::resolve);
    }

    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup(1);
        int workerThreads = profile.isDev()
                ? 2
                : Math.max(4, Runtime.getRuntime().availableProcessors() * 2);
        workerGroup = new NioEventLoopGroup(workerThreads);
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, profile.isDev() ? 128 : 1024)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, profile.isProd())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socket) {
                        socket.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new ReadTimeoutHandler(timeouts.request().toSeconds(), TimeUnit.SECONDS))
                                .addLast(new IdleStateHandler(0, 0, timeouts.idle().toSeconds(), TimeUnit.SECONDS))
                                .addLast(new HttpObjectAggregator(maxRequestBytes + HEADER_SLACK))
                                .addLast(new ChunkedWriteHandler())
                                .addLast(new RequestHandler());
                    }
                });
        channel = bootstrap.bind(host, port).syncUninterruptibly().channel();
    }

    @Override
    public void stop(int delaySeconds) {
        if (channel != null) {
            channel.close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully(0, delaySeconds, TimeUnit.SECONDS).syncUninterruptibly();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully(0, delaySeconds, TimeUnit.SECONDS).syncUninterruptibly();
        }
    }

    private final class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest message) {
            handle(ctx, message);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            int status;
            String text;
            if (cause instanceof TooLongFrameException) {
                status = 413;
                text = "Payload Too Large";
            } else if (cause instanceof ReadTimeoutException) {
                status = 408;
                text = "Request Timeout";
            } else {
                status = 500;
                text = "Internal Server Error";
            }
            ChannelFuture future = send(ctx, status, text, false);
            future.addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
            if (event instanceof IdleStateEvent) {
                ctx.close();
                return;
            }
            super.userEventTriggered(ctx, event);
        }
    }

    private void handle(ChannelHandlerContext ctx, FullHttpRequest message) {
        HttpMethod method;
        try {
            method = HttpMethod.valueOf(message.method().name());
        } catch (IllegalArgumentException exception) {
            send(ctx, 405, "Method Not Allowed", HttpUtil.isKeepAlive(message));
            return;
        }

        String path;
        String rawQuery;
        try {
            URI uri = new URI(message.uri());
            path = uri.getPath();
            rawQuery = uri.getRawQuery();
        } catch (URISyntaxException exception) {
            send(ctx, 400, "Bad Request", HttpUtil.isKeepAlive(message));
            return;
        }

        Response response;
        try {
            Map<String, List<String>> headers = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : message.headers()) {
                headers.computeIfAbsent(entry.getKey(), ignored -> new ArrayList<>()).add(entry.getValue());
            }
            String remote = ctx.channel().remoteAddress() instanceof InetSocketAddress address
                    ? address.getAddress().getHostAddress()
                    : "";
            byte[] body = new byte[message.content().readableBytes()];
            message.content().readBytes(body);
            if (body.length > maxRequestBytes) {
                send(ctx, 413, "Payload Too Large", HttpUtil.isKeepAlive(message));
                return;
            }
            Request request = new Request(method, path, headers, Request.parseQuery(rawQuery), Map.of(), body,
                    trustedProxyResolver.resolve(remote, headers));
            response = kernel.handle(request);
        } catch (RuntimeException exception) {
            send(ctx, 400, "Bad Request", HttpUtil.isKeepAlive(message));
            return;
        }

        boolean head = message.method().name().equalsIgnoreCase("HEAD");
        Optional<InputStream> stream = head ? Optional.empty() : response.bodyStream();
        if (stream.isPresent()) {
            writeStream(ctx, message, response, stream.get());
            return;
        }
        byte[] body = head ? new byte[0] : response.body();
        write(ctx, message, response.status(), body, response.headers());
    }

    private ChannelFuture writeStream(ChannelHandlerContext ctx, FullHttpRequest message,
                                      Response response, InputStream input) {
        boolean keepAlive = message != null && HttpUtil.isKeepAlive(message);
        HttpResponse outgoing = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.valueOf(response.status()));
        response.headers().forEach((name, value) -> outgoing.headers().set(name, value));
        long length = response.contentLength();
        if (length >= 0) {
            outgoing.headers().set(HttpHeaderNames.CONTENT_LENGTH, length);
        }
        HttpUtil.setKeepAlive(outgoing, keepAlive);
        ctx.write(outgoing);
        ctx.write(new ChunkedStream(input, 8192));
        ChannelFuture last = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        last.addListener(ignored -> {
            try {
                input.close();
            } catch (IOException exception) {
                // The stream may already be closed by the chunked writer.
            }
        });
        if (!keepAlive) {
            last.addListener(ChannelFutureListener.CLOSE);
        }
        return last;
    }

    private ChannelFuture send(ChannelHandlerContext ctx, int status, String text, boolean keepAlive) {
        return write(ctx, null, status, text.getBytes(StandardCharsets.UTF_8),
                Map.of("Content-Type", "text/plain; charset=utf-8"), keepAlive);
    }

    private ChannelFuture write(ChannelHandlerContext ctx, FullHttpRequest message, int status, byte[] body,
                                Map<String, String> headers) {
        return write(ctx, message, status, body, headers, message != null && HttpUtil.isKeepAlive(message));
    }

    private ChannelFuture write(ChannelHandlerContext ctx, FullHttpRequest message, int status, byte[] body,
                                Map<String, String> headers, boolean keepAlive) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(status), Unpooled.wrappedBuffer(body));
        headers.forEach((name, value) -> response.headers().set(name, value));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.length);
        HttpUtil.setKeepAlive(response, keepAlive);
        ChannelFuture future = ctx.writeAndFlush(response);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
        return future;
    }
}

package io.javelin.http.jdk;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.http.common.StaticAssetResolver;
import io.javelin.http.common.HttpTimeoutConfig;
import io.javelin.http.common.StaticCachePolicy;
import io.javelin.http.common.TrustedProxyResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class JdkHttpServer implements HttpServerAdapter {
    private static final int DEFAULT_MAX_REQUEST_BYTES = 1_048_576;

    private final HttpServer server;
    private final ExecutorService executor;
    private final HttpKernel kernel;
    private final TrustedProxyResolver trustedProxyResolver;
    private final StaticAssetResolver staticAssetResolver;
    private final int maxRequestBytes;
    private final HttpTimeoutConfig timeouts;
    private final ScheduledExecutorService watchdog;

    public JdkHttpServer(String host, int port, HttpKernel kernel) {
        this(host, port, kernel, List.of(), Path.of("public"), DEFAULT_MAX_REQUEST_BYTES);
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies) {
        this(host, port, kernel, trustedProxies, Path.of("public"), DEFAULT_MAX_REQUEST_BYTES);
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot) {
        this(host, port, kernel, trustedProxies, staticRoot, DEFAULT_MAX_REQUEST_BYTES);
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot, int maxRequestBytes) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, StaticCachePolicy.DEFAULT);
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                         int maxRequestBytes, StaticCachePolicy cachePolicy) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, cachePolicy, HttpTimeoutConfig.DEFAULT);
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                         int maxRequestBytes, StaticCachePolicy cachePolicy, HttpTimeoutConfig timeouts) {
        this(host, port, kernel, new TrustedProxyResolver(trustedProxies),
                new StaticAssetResolver(staticRoot, cachePolicy), maxRequestBytes, timeouts);
    }

    JdkHttpServer(String host, int port, HttpKernel kernel, TrustedProxyResolver trustedProxyResolver,
                  StaticAssetResolver staticAssetResolver, int maxRequestBytes) {
        this(host, port, kernel, trustedProxyResolver, staticAssetResolver, maxRequestBytes, HttpTimeoutConfig.DEFAULT);
    }

    JdkHttpServer(String host, int port, HttpKernel kernel, TrustedProxyResolver trustedProxyResolver,
                  StaticAssetResolver staticAssetResolver, int maxRequestBytes, HttpTimeoutConfig timeouts) {
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to bind HTTP server", exception);
        }
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.trustedProxyResolver = trustedProxyResolver;
        this.staticAssetResolver = staticAssetResolver;
        this.maxRequestBytes = maxRequestBytes;
        this.timeouts = timeouts;
        ThreadFactory daemon = task -> {
            Thread thread = new Thread(task, "javelin-http-timeout");
            thread.setDaemon(true);
            return thread;
        };
        this.watchdog = Executors.newSingleThreadScheduledExecutor(daemon);
        this.kernel = kernel.withStaticFallback(staticAssetResolver::resolve);
        this.server.setExecutor(executor);
        this.server.createContext("/", this::handle);
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop(int delaySeconds) {
        server.stop(delaySeconds);
        executor.close();
        watchdog.shutdownNow();
    }

    private void handle(HttpExchange exchange) throws IOException {
        ScheduledFuture<?> deadline = watchdog.schedule(() -> exchange.close(),
                timeouts.request().toMillis(), TimeUnit.MILLISECONDS);
        try {
            Request request;
            try {
                request = toRequest(exchange);
            } catch (RequestRejected rejected) {
                write(exchange, errorResponse(rejected.status));
                return;
            }
            write(exchange, kernel.handle(request));
        } finally {
            deadline.cancel(false);
        }
    }

    private static Response errorResponse(int status) {
        return switch (status) {
            case 413 -> Response.text("Payload Too Large", 413);
            case 400 -> Response.text("Bad Request", 400);
            default -> Response.text("Method Not Allowed", 405);
        };
    }

    private void write(HttpExchange exchange, Response response) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        response.headers().forEach(headers::set);
        boolean head = exchange.getRequestMethod().equalsIgnoreCase("HEAD");
        Optional<InputStream> stream = head ? Optional.empty() : response.bodyStream();
        if (stream.isPresent()) {
            long length = response.contentLength();
            exchange.sendResponseHeaders(response.status(), length == 0 ? -1 : length);
            try (InputStream input = stream.get(); OutputStream output = exchange.getResponseBody()) {
                input.transferTo(output);
            }
        } else {
            byte[] body = head ? new byte[0] : response.body();
            exchange.sendResponseHeaders(response.status(), body.length == 0 ? -1 : body.length);
            if (body.length > 0) {
                exchange.getResponseBody().write(body);
            }
        }
        exchange.close();
    }

    private Request toRequest(HttpExchange exchange) throws IOException {
        HttpMethod method = parseMethod(exchange.getRequestMethod());
        if (method == null) {
            throw new RequestRejected(405, "Unsupported HTTP method");
        }
        Headers headers = exchange.getRequestHeaders();
        String remote = exchange.getRemoteAddress() == null ? "" : exchange.getRemoteAddress().getAddress().getHostAddress();
        String clientAddress = trustedProxyResolver.resolve(remote, headers);
        String path = exchange.getRequestURI().getPath();
        Map<String, String> query;
        try {
            query = Request.parseQuery(exchange.getRequestURI().getRawQuery());
        } catch (RuntimeException exception) {
            throw new RequestRejected(400, "Malformed query string");
        }
        return new Request(method, path, headers, query, Map.of(), readBody(exchange.getRequestBody()), clientAddress);
    }

    private byte[] readBody(InputStream input) throws IOException {
        byte[] bytes = input.readNBytes(maxRequestBytes + 1);
        if (bytes.length > maxRequestBytes) {
            throw new RequestRejected(413, "Request body too large");
        }
        return bytes;
    }

    private static HttpMethod parseMethod(String value) {
        try {
            return HttpMethod.valueOf(value);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    private static final class RequestRejected extends RuntimeException {
        private final int status;

        private RequestRejected(int status, String message) {
            super(message);
            this.status = status;
        }
    }
}

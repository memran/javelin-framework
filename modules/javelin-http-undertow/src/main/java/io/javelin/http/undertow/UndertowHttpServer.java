package io.javelin.http.undertow;

import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.http.common.StaticAssetResolver;
import io.javelin.http.common.StaticCachePolicy;
import io.javelin.http.common.TrustedProxyResolver;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class UndertowHttpServer implements HttpServerAdapter {
    private final String host;
    private final int port;
    private final int maxRequestBytes;
    private final HttpKernel kernel;
    private final TrustedProxyResolver trustedProxyResolver;
    private final StaticAssetResolver staticAssetResolver;

    private Undertow server;

    public UndertowHttpServer(String host, int port, HttpKernel kernel) {
        this(host, port, kernel, List.of(), Path.of("public"), 1_048_576);
    }

    public UndertowHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies) {
        this(host, port, kernel, trustedProxies, Path.of("public"), 1_048_576);
    }

    public UndertowHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot) {
        this(host, port, kernel, trustedProxies, staticRoot, 1_048_576);
    }

    public UndertowHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot, int maxRequestBytes) {
        this(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes, StaticCachePolicy.DEFAULT);
    }

    public UndertowHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot,
                              int maxRequestBytes, StaticCachePolicy cachePolicy) {
        this.host = host;
        this.port = port;
        this.maxRequestBytes = maxRequestBytes;
        this.trustedProxyResolver = new TrustedProxyResolver(trustedProxies);
        this.staticAssetResolver = new StaticAssetResolver(staticRoot, cachePolicy);
        this.kernel = kernel.withStaticFallback(staticAssetResolver::resolve);
    }

    @Override
    public void start() {
        HttpHandler handler = exchange -> {
            if (exchange.isInIoThread()) {
                exchange.dispatch(this::handle);
            } else {
                handle(exchange);
            }
        };
        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(handler)
                .build();
        server.start();
    }

    @Override
    public void stop(int delaySeconds) {
        if (server != null) {
            server.stop();
        }
    }

    private void handle(HttpServerExchange exchange) throws IOException {
        exchange.startBlocking();

        HttpMethod method;
        try {
            method = HttpMethod.valueOf(exchange.getRequestMethod().toString());
        } catch (IllegalArgumentException exception) {
            error(exchange, 405, "Method Not Allowed");
            return;
        }

        Map<String, List<String>> headers = new LinkedHashMap<>();
        for (HeaderValues header : exchange.getRequestHeaders()) {
            List<String> values = new ArrayList<>();
            for (String value : header) {
                values.add(value);
            }
            headers.put(header.getHeaderName().toString(), values);
        }

        byte[] body = exchange.getInputStream().readNBytes(maxRequestBytes + 1);
        if (body.length > maxRequestBytes) {
            error(exchange, 413, "Payload Too Large");
            return;
        }

        String remote = exchange.getSourceAddress() instanceof InetSocketAddress address
                ? address.getAddress().getHostAddress()
                : "";
        Response response;
        try {
            Request request = new Request(method, exchange.getRequestPath(), headers,
                    Request.parseQuery(exchange.getQueryString()), Map.of(), body,
                    trustedProxyResolver.resolve(remote, headers));
            response = kernel.handle(request);
        } catch (RuntimeException exception) {
            error(exchange, 400, "Bad Request");
            return;
        }

        boolean head = exchange.getRequestMethod().toString().equalsIgnoreCase("HEAD");
        byte[] responseBody = head ? new byte[0] : response.body();
        exchange.setStatusCode(response.status());
        response.headers().forEach((name, value) -> exchange.getResponseHeaders().put(new HttpString(name), value));
        exchange.setResponseContentLength(responseBody.length);
        if (responseBody.length > 0) {
            exchange.getOutputStream().write(responseBody);
        }
        exchange.endExchange();
    }

    private static void error(HttpServerExchange exchange, int status, String text) throws IOException {
        byte[] body = text.getBytes(StandardCharsets.UTF_8);
        exchange.setStatusCode(status);
        exchange.getResponseHeaders().put(new HttpString("Content-Type"), "text/plain; charset=utf-8");
        exchange.setResponseContentLength(body.length);
        exchange.getOutputStream().write(body);
        exchange.endExchange();
    }
}

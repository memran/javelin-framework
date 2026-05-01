package io.javelin.http.jdk;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class JdkHttpServer implements HttpServerAdapter {
    private final HttpServer server;
    private final ExecutorService executor;
    private final HttpKernel kernel;
    private final TrustedProxyResolver trustedProxyResolver;
    private final StaticAssetResolver staticAssetResolver;

    public JdkHttpServer(String host, int port, HttpKernel kernel) {
        this(host, port, kernel, List.of(), Path.of("public"));
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies) {
        this(host, port, kernel, trustedProxies, Path.of("public"));
    }

    public JdkHttpServer(String host, int port, HttpKernel kernel, List<String> trustedProxies, Path staticRoot) {
        this(host, port, kernel, new TrustedProxyResolver(trustedProxies), new StaticAssetResolver(staticRoot));
    }

    JdkHttpServer(String host, int port, HttpKernel kernel, TrustedProxyResolver trustedProxyResolver, StaticAssetResolver staticAssetResolver) {
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to bind HTTP server", exception);
        }
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.kernel = kernel;
        this.trustedProxyResolver = trustedProxyResolver;
        this.staticAssetResolver = staticAssetResolver;
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
    }

    private void handle(HttpExchange exchange) throws IOException {
        Request request = toRequest(exchange);
        Response response = staticAssetResolver.resolve(exchange.getRequestMethod(), request.path())
                .orElseGet(() -> kernel.handle(request));
        Headers headers = exchange.getResponseHeaders();
        response.headers().forEach(headers::set);
        boolean head = exchange.getRequestMethod().equalsIgnoreCase("HEAD");
        byte[] body = head ? new byte[0] : response.body();
        exchange.sendResponseHeaders(response.status(), head ? -1 : body.length);
        if (!head) {
            exchange.getResponseBody().write(body);
        }
        exchange.close();
    }

    private Request toRequest(HttpExchange exchange) throws IOException {
        Map<String, List<String>> headers = exchange.getRequestHeaders();
        String remote = exchange.getRemoteAddress() == null ? "" : exchange.getRemoteAddress().getAddress().getHostAddress();
        String clientAddress = trustedProxyResolver.resolve(remote, exchange.getRequestHeaders());
        String path = exchange.getRequestURI().getPath();
        return new Request(
                HttpMethod.valueOf(exchange.getRequestMethod()),
                path,
                headers,
                Request.parseQuery(exchange.getRequestURI().getRawQuery()),
                Map.of(),
                exchange.getRequestBody().readAllBytes(),
                clientAddress
        );
    }
}

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class JdkHttpServer implements HttpServerAdapter {
    private final HttpServer server;
    private final ExecutorService executor;
    private final HttpKernel kernel;

    public JdkHttpServer(String host, int port, HttpKernel kernel) {
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to bind HTTP server", exception);
        }
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.kernel = kernel;
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
        Response response = kernel.handle(toRequest(exchange));
        Headers headers = exchange.getResponseHeaders();
        response.headers().forEach(headers::set);
        byte[] body = response.body();
        exchange.sendResponseHeaders(response.status(), body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }

    private Request toRequest(HttpExchange exchange) throws IOException {
        Map<String, List<String>> headers = exchange.getRequestHeaders();
        String remote = exchange.getRemoteAddress() == null ? "" : exchange.getRemoteAddress().getAddress().getHostAddress();
        String path = exchange.getRequestURI().getPath();
        return new Request(
                HttpMethod.valueOf(exchange.getRequestMethod()),
                path,
                headers,
                Request.parseQuery(exchange.getRequestURI().getRawQuery()),
                Map.of(),
                exchange.getRequestBody().readAllBytes(),
                remote
        );
    }
}

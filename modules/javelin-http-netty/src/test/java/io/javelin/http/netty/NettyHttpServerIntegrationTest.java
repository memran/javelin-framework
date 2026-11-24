package io.javelin.http.netty;

import io.javelin.core.AppProfile;
import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.HttpKernel;
import io.javelin.core.Response;
import io.javelin.core.Route;
import io.javelin.core.Router;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;
import io.javelin.core.security.RequiresPermissionMiddleware;
import io.javelin.http.common.HttpTimeoutConfig;
import io.javelin.http.common.StaticCachePolicy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class NettyHttpServerIntegrationTest {
    private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

    @Test
    void servesStaticAssetsAndFallsBackToTheKernel() throws Exception {
        Path root = Files.createTempDirectory("javelin-netty-static");
        Files.writeString(root.resolve("index.html"), "<h1>Home</h1>", StandardCharsets.UTF_8);
        Router router = new Router();
        router.get("/dynamic", request -> Response.text("kernel:" + request.path()));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, root, 1_048_576)) {
            assertEquals("<h1>Home</h1>", get(server, "/").body());
            assertEquals("kernel:/dynamic", get(server, "/dynamic").body());
        }
    }

    @Test
    void headRequestsFallBackToGetRoutes() throws Exception {
        Router router = new Router();
        router.get("/dynamic", request -> Response.text("hello"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, Files.createTempDirectory("javelin-netty-head"), 1_048_576)) {
            HttpRequest request = HttpRequest.newBuilder(uri(server, "/dynamic"))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            assertEquals(200, response.statusCode());
            assertEquals("", response.body());
        }
    }

    @Test
    void returns405ForUnsupportedMethods() throws Exception {
        HttpKernel kernel = new HttpKernel(new Router(), new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, Files.createTempDirectory("javelin-netty-method"), 1_048_576)) {
            HttpRequest request = HttpRequest.newBuilder(uri(server, "/dynamic"))
                    .method("TRACE", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            assertEquals(405, response.statusCode());
        }
    }

    @Test
    void returnsAllowHeaderAndEnforcesBearerAuthentication() throws Exception {
        Router router = new Router();
        router.post("/only-post", request -> Response.text("posted"));
        router.add(Route.get("/secure",
                        request -> Response.text("secret:" + ((Authenticated) request
                                .attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElseThrow()).subject()))
                .middleware(new BearerAuthMiddleware(token -> "good".equals(token)
                        ? Optional.of(new Authenticated("alice", Set.of("secure.read")))
                        : Optional.empty()))
                .middleware(new RequiresPermissionMiddleware("secure.read")));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, Files.createTempDirectory("javelin-netty-security"), 1_048_576)) {
            HttpResponse<String> wrongMethod = get(server, "/only-post");
            HttpResponse<String> missing = get(server, "/secure");
            HttpResponse<String> invalid = get(server, "/secure", "Authorization", "Bearer bad");
            HttpResponse<String> valid = get(server, "/secure", "Authorization", "Bearer good");

            assertEquals(405, wrongMethod.statusCode());
            assertEquals("POST", wrongMethod.headers().firstValue("Allow").orElse(""));
            assertEquals(401, missing.statusCode());
            assertEquals("Bearer", missing.headers().firstValue("WWW-Authenticate").orElse(""));
            assertEquals(401, invalid.statusCode());
            assertEquals(200, valid.statusCode());
            assertEquals("secret:alice", valid.body());
        }
    }

    @Test
    void rejectsOversizedBodies() throws Exception {
        Router router = new Router();
        router.post("/upload", request -> Response.text("ok"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, Files.createTempDirectory("javelin-netty-limit"), 64)) {
            HttpRequest request = HttpRequest.newBuilder(uri(server, "/upload"))
                    .POST(HttpRequest.BodyPublishers.ofByteArray("x".repeat(128).getBytes(StandardCharsets.UTF_8)))
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            assertEquals(413, response.statusCode());
        }
    }

    @Test
    void returns408ForRequestsThatExceedTheConfiguredReadTimeout() throws Exception {
        int port = freePort();
        NettyHttpServer server = new NettyHttpServer("127.0.0.1", port,
                new HttpKernel(new Router(), new DefaultExceptionHandler()), List.of(),
                Files.createTempDirectory("javelin-netty-timeout"), 1_048_576, AppProfile.DEV,
                StaticCachePolicy.DEFAULT, new HttpTimeoutConfig(Duration.ofSeconds(1), Duration.ofSeconds(10)));
        server.start();
        try {
            try (Socket socket = new Socket("127.0.0.1", port)) {
                socket.setSoTimeout(5000);
                socket.getOutputStream().write("GET / HTTP/1.1\r\nHost: localhost\r\n".getBytes(StandardCharsets.UTF_8));
                socket.getOutputStream().flush();
                byte[] buffer = new byte[256];
                int read = socket.getInputStream().read(buffer);
                String response = new String(buffer, 0, Math.max(read, 0), StandardCharsets.UTF_8);
                assertTrue(response.contains("408"), response);
            }
        } finally {
            server.stop(1);
        }
    }

    private static RunningServer startServer(HttpKernel kernel, Path staticRoot, int maxRequestBytes) throws IOException {
        int port = freePort();
        NettyHttpServer server = new NettyHttpServer("127.0.0.1", port, kernel, List.of(), staticRoot, maxRequestBytes);
        server.start();
        return new RunningServer(server, port);
    }

    private static int freePort() throws IOException {
        try (ServerSocket socket = new ServerSocket()) {
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress("127.0.0.1", 0));
            return socket.getLocalPort();
        }
    }

    private static HttpResponse<String> get(RunningServer server, String path) throws Exception {
        return get(server, path, null, null);
    }

    private static HttpResponse<String> get(RunningServer server, String path, String header, String value) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri(server, path))
                .GET()
                .timeout(Duration.ofSeconds(5));
        if (header != null) {
            builder.header(header, value);
        }
        return CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private static URI uri(RunningServer server, String path) {
        return URI.create("http://127.0.0.1:" + server.port() + path);
    }

    private record RunningServer(NettyHttpServer server, int port) implements AutoCloseable {
        @Override
        public void close() {
            server.stop(1);
        }
    }
}

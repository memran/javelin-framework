package io.javelin.http.jdk;

import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.HttpKernel;
import io.javelin.core.Response;
import io.javelin.core.Router;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.ServerSocket;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class JdkHttpServerIntegrationTest {
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    @Test
    void servesStaticAssetsAndFallsBackToTheKernelOnARealServer() throws Exception {
        Path root = Files.createTempDirectory("javelin-http-static");
        Files.writeString(root.resolve("index.html"), "<h1>Home</h1>", StandardCharsets.UTF_8);
        Files.createDirectories(root.resolve("docs"));
        Files.writeString(root.resolve("docs/index.html"), "<p>Docs</p>", StandardCharsets.UTF_8);
        Files.createDirectories(root.resolve("assets"));
        Files.writeString(root.resolve("assets/app.css"), "body { color: #111; }", StandardCharsets.UTF_8);

        Router router = new Router();
        router.get("/dynamic", request -> Response.text("kernel:" + request.path()));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, List.of(), root)) {
            HttpResponse<String> index = get(server, "/");
            HttpResponse<String> docs = get(server, "/docs/");
            HttpResponse<String> kernelResponse = get(server, "/dynamic");
            HttpResponse<String> head = head(server, "/assets/app.css");

            assertEquals(200, index.statusCode());
            assertEquals("<h1>Home</h1>", index.body());
            assertEquals("text/html; charset=utf-8", header(index, "Content-Type"));
            assertEquals("<p>Docs</p>", docs.body());
            assertEquals("kernel:/dynamic", kernelResponse.body());
            assertEquals(200, head.statusCode());
            assertEquals("", head.body());
            assertEquals("text/css; charset=utf-8", header(head, "Content-Type"));
        }
    }

    @Test
    void parsesMultipartRequestsAndSavesUploadedFilesOnARealServer() throws Exception {
        Path root = Files.createTempDirectory("javelin-http-multipart");
        Path uploads = root.resolve("uploads");
        Files.createDirectories(uploads);

        Router router = new Router();
        router.post("/upload", request -> {
            Path savedAvatar = request.saveFile("avatar", uploads).orElseThrow();
            List<Path> savedAttachments = request.saveFiles("attachment", uploads);
            return Response.text(String.join("|",
                    request.input().text("title").orElseThrow(),
                    request.input().text("notes").orElse(""),
                    Boolean.toString(request.file("notes").isPresent()),
                    request.file("avatar").map(file -> file.clientFilename()).orElseThrow(),
                    request.file("avatar").map(file -> file.safeFilename()).orElseThrow(),
                    Integer.toString(request.files("attachment").size()),
                    Integer.toString(savedAttachments.size()),
                    savedAvatar.getFileName().toString(),
                    request.uploads().size() + ""
            ));
        });
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, List.of(), root)) {
            HttpResponse<String> response = postMultipart(server, "/upload", multipartBody(
                    "----JavelinBoundaryUpload",
                    multipartField("title", "Hello <b>Javelin</b>"),
                    multipartFile("notes", "", "text/plain", "read me"),
                    multipartFile("avatar", "../avatar.txt", "text/plain", "avatar-body"),
                    multipartFile("attachment", "first.txt", "text/plain", "one"),
                    multipartFile("attachment", "second.txt", "text/plain", "two")
            ));

            assertEquals(200, response.statusCode());
            assertEquals("Hello Javelin|read me|false|../avatar.txt|avatar.txt|2|2|avatar.txt|3", response.body());
            assertTrue(Files.exists(uploads.resolve("avatar.txt")));
            assertTrue(Files.exists(uploads.resolve("first.txt")));
            assertTrue(Files.exists(uploads.resolve("second.txt")));
            assertEquals("avatar-body", Files.readString(uploads.resolve("avatar.txt")));
            assertEquals("one", Files.readString(uploads.resolve("first.txt")));
            assertEquals("two", Files.readString(uploads.resolve("second.txt")));
        }
    }

    @Test
    void resolvesXForwardedForOnlyWhenTheRemotePeerIsTrusted() throws Exception {
        HttpKernel kernel = new HttpKernel(routeForIp(), new DefaultExceptionHandler());

        try (RunningServer trusted = startServer(kernel, List.of("127.0.0.1"), Files.createTempDirectory("javelin-http-proxy"))) {
            HttpResponse<String> response = get(trusted, "/ip", Map.of("X-Forwarded-For", "203.0.113.10, 127.0.0.1"));
            assertEquals("203.0.113.10", response.body());
        }

        try (RunningServer untrusted = startServer(kernel, List.of(), Files.createTempDirectory("javelin-http-proxy-untrusted"))) {
            HttpResponse<String> response = get(untrusted, "/ip", Map.of("X-Forwarded-For", "203.0.113.10, 127.0.0.1"));
            assertEquals("127.0.0.1", response.body());
        }
    }

    @Test
    void resolvesForwardedHeaderWhenTheRemotePeerIsTrusted() throws Exception {
        HttpKernel kernel = new HttpKernel(routeForIp(), new DefaultExceptionHandler());

        try (RunningServer server = startServer(kernel, List.of("127.0.0.1"), Files.createTempDirectory("javelin-http-forwarded"))) {
            HttpResponse<String> response = get(server, "/ip", Map.of("Forwarded", "for=\"[2001:db8::1]\";proto=https;host=example.com"));
            assertEquals("2001:db8::1", response.body());
        }
    }

    private static Router routeForIp() {
        Router router = new Router();
        router.get("/ip", request -> Response.text(request.remoteAddress()));
        return router;
    }

    private static RunningServer startServer(HttpKernel kernel, List<String> trustedProxies, Path staticRoot) throws IOException {
        int port = freePort();
        JdkHttpServer server = new JdkHttpServer("127.0.0.1", port, kernel, trustedProxies, staticRoot);
        server.start();
        awaitReady(port);
        return new RunningServer(server, port);
    }

    private static int freePort() throws IOException {
        try (ServerSocket socket = new ServerSocket()) {
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress("127.0.0.1", 0));
            return socket.getLocalPort();
        }
    }

    private static void awaitReady(int port) throws IOException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
        IOException failure = null;
        while (System.nanoTime() < deadline) {
            try {
                CLIENT.send(HttpRequest.newBuilder(uri(port, "/"))
                                .timeout(Duration.ofMillis(250))
                                .GET()
                                .build(),
                        HttpResponse.BodyHandlers.discarding());
                return;
            } catch (IOException exception) {
                failure = exception;
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                break;
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        if (failure != null) {
            throw failure;
        }
    }

    private static HttpResponse<String> get(RunningServer server, String path) throws Exception {
        return get(server, path, Map.of());
    }

    private static HttpResponse<String> get(RunningServer server, String path, Map<String, String> headers) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri(server, path))
                .GET()
                .timeout(Duration.ofSeconds(5));
        headers.forEach(builder::header);
        return CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private static HttpResponse<String> head(RunningServer server, String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri(server, path))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.ofSeconds(5))
                .build();
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private static HttpResponse<String> postMultipart(RunningServer server, String path, byte[] body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri(server, path))
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .header("Content-Type", "multipart/form-data; boundary=----JavelinBoundaryUpload")
                .timeout(Duration.ofSeconds(5))
                .build();
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private static URI uri(RunningServer server, String path) {
        return uri(server.port(), path);
    }

    private static URI uri(int port, String path) {
        return URI.create("http://127.0.0.1:" + port + path);
    }

    private static String header(HttpResponse<?> response, String name) {
        return response.headers().firstValue(name).orElse("");
    }

    private static byte[] multipartBody(String boundary, String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append("--").append(boundary).append("\r\n").append(part);
            if (!part.endsWith("\r\n")) {
                builder.append("\r\n");
            }
        }
        builder.append("--").append(boundary).append("--\r\n");
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String multipartField(String name, String value) {
        return """
                Content-Disposition: form-data; name="%s"

                %s
                """.formatted(name, value).replace("\n", "\r\n");
    }

    private static String multipartFile(String name, String filename, String contentType, String body) {
        return """
                Content-Disposition: form-data; name="%s"; filename="%s"
                Content-Type: %s

                %s
                """.formatted(name, filename, contentType, body).replace("\n", "\r\n");
    }

    private record RunningServer(JdkHttpServer server, int port) implements AutoCloseable {
        @Override
        public void close() {
            server.stop(0);
        }
    }
}

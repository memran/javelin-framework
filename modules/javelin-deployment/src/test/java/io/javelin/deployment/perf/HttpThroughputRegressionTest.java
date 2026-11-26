package io.javelin.deployment.perf;

import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.HttpKernel;
import io.javelin.core.Response;
import io.javelin.core.Router;
import io.javelin.deployment.BenchmarkRunner;
import io.javelin.http.jdk.JdkHttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** End-to-end throughput floor through the real JDK HTTP adapter. */
final class HttpThroughputRegressionTest {
    private static final PerformanceReport REPORT = new PerformanceReport("http-jdk");

    @AfterAll
    static void publish() {
        REPORT.publish();
    }

    @Test
    void sustainsLoopbackThroughputThroughTheJdkAdapter() throws Exception {
        Router router = new Router();
        router.get("/perf", request -> Response.text("ok"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        int port = freePort();
        Path staticRoot = Files.createTempDirectory("javelin-perf");
        JdkHttpServer server = new JdkHttpServer("127.0.0.1", port, kernel, List.of(), staticRoot);
        server.start();
        try {
            awaitReady(port);
            String url = URI.create("http://127.0.0.1:" + port + "/perf").toString();
            BenchmarkRunner runner = new BenchmarkRunner();
            runner.run(url, 4, Duration.ofMillis(250));
            BenchmarkRunner.Result result = runner.run(url, 8, Duration.ofMillis(700));

            assertEquals(result.successes(), result.total(), "every request should succeed");
            assertTrue(result.p95Ms() < 500, "p95 latency should stay below 500ms on loopback");
            REPORT.record(new PerformanceReport.Measurement("http.jdk.throughput", result.total(),
                    result.durationMs(), 1_000_000_000.0 / Math.max(1, result.requestsPerSecond()),
                    result.requestsPerSecond(), 500));
        } finally {
            server.stop(0);
        }
    }

    private static int freePort() throws Exception {
        try (ServerSocket socket = new ServerSocket()) {
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress("127.0.0.1", 0));
            return socket.getLocalPort();
        }
    }

    private static void awaitReady(int port) throws Exception {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(250)).build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/perf"))
                .timeout(Duration.ofMillis(250))
                .GET()
                .build();
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (System.nanoTime() < deadline) {
            try {
                HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                if (response.statusCode() == 200) {
                    return;
                }
            } catch (java.io.IOException exception) {
                Thread.sleep(25);
            }
        }
        throw new IllegalStateException("HTTP server did not become ready on port " + port);
    }
}

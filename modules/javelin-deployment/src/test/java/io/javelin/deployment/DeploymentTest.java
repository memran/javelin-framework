package io.javelin.deployment;

import com.sun.net.httpserver.HttpServer;
import io.javelin.core.Config;
import io.javelin.core.Env;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DeploymentTest {
    @Test
    void productionValidationReportsUnsafeDefaults() {
        List<ValidationIssue> issues = new ProductionValidator().validate(
                new TestConfig(Map.of(
                        "app.name", "demo",
                        "mail.driver", "test",
                        "security.jwt.secret", "change-me",
                        "queue.driver", "file",
                        "scheduler.history", "memory")),
                key -> key.equals("APP_ENV") ? Optional.of("prod") : Optional.empty());

        assertTrue(issues.stream().anyMatch(issue -> issue.key().equals("mail.driver")));
        assertTrue(issues.stream().anyMatch(issue -> issue.key().equals("security.jwt.secret")));
        assertTrue(issues.stream().anyMatch(issue -> issue.key().equals("scheduler.history")));
    }

    @Test
    void systemdUnitContainsServiceDirectives() {
        String unit = new SystemdUnit("demo", "javelin", "/opt/demo", "/opt/demo/app.jar", "", "/opt/demo/.env")
                .render();
        assertTrue(unit.contains("[Unit]"));
        assertTrue(unit.contains("ExecStart=java -jar /opt/demo/app.jar serve"));
        assertTrue(unit.contains("Restart=on-failure"));
        assertTrue(unit.contains("WantedBy=multi-user.target"));
    }

    @Test
    void graalvmConfigIsWritten(@TempDir Path dir) throws Exception {
        List<Path> files = new GraalvmConfigGenerator().write(dir);
        assertEquals(2, files.size());
        assertTrue(Files.readString(files.get(0)).contains("io.javelin.core.Application"));
        assertTrue(Files.readString(files.get(1)).contains("config/.*"));
    }

    @Test
    void benchmarkRunnerReportsThroughput() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/health", exchange -> {
            byte[] body = "{\"status\":\"ok\"}".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.start();
        try {
            String url = "http://127.0.0.1:" + server.getAddress().getPort() + "/health";
            BenchmarkRunner.Result result = new BenchmarkRunner().run(url, 2, Duration.ofMillis(300));
            assertTrue(result.total() > 0);
            assertEquals(result.total(), result.successes() + result.failures());
            assertTrue(result.requestsPerSecond() > 0);
        } finally {
            server.stop(0);
        }
    }

    private record TestConfig(Map<String, String> values) implements Config {
        @Override public Optional<String> getString(String key) { return Optional.ofNullable(values.get(key)); }
        @Override public Optional<Integer> getInt(String key) { return Optional.empty(); }
        @Override public Optional<Boolean> getBoolean(String key) { return Optional.empty(); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }
}

package io.javelin.observability;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ObservabilityTest {
    @Test
    void metricsRegistryTracksCountersAndGauges() {
        MetricsRegistry metrics = new MetricsRegistry();
        metrics.increment("jobs", 3);
        metrics.gauge("queue.depth", 7);
        assertEquals(3L, metrics.counter("jobs"));
        assertEquals(7, metrics.gauge("queue.depth"));
        assertTrue(metrics.snapshot().containsKey("jobs"));
    }

    @Test
    void middlewareServesHealthReadyAndMetrics() throws Exception {
        Config config = new TestConfig();
        Application app = new Application(config, key -> Optional.empty());
        ObservabilityConfig settings = new ObservabilityConfig(true, "/health", "/ready", "/metrics", "none");
        MetricsRegistry metrics = new MetricsRegistry();
        HealthRegistry health = new HealthRegistry();
        health.live("memory", Health::up);
        health.ready("application", Health::up);
        ObservabilityMiddleware middleware = new ObservabilityMiddleware(app, settings, metrics, health);

        Response live = middleware.handle(request("/health"), ignored -> Response.text("ok"));
        assertEquals(200, live.status());
        assertTrue(body(live).contains("\"up\""));

        Response ready = middleware.handle(request("/ready"), ignored -> Response.text("ok"));
        assertEquals(200, ready.status());

        Response reporting = middleware.handle(request("/metrics"), ignored -> Response.text("ok"));
        assertTrue(body(reporting).contains("runtime"));
        assertTrue(body(reporting).contains("pools"));
    }

    @Test
    void failingReadinessReturns503() throws Exception {
        Application app = new Application(new TestConfig(), key -> Optional.empty());
        HealthRegistry health = new HealthRegistry();
        health.ready("database", () -> Health.down("unreachable"));
        ObservabilityMiddleware middleware = new ObservabilityMiddleware(app,
                new ObservabilityConfig(true, "/health", "/ready", "/metrics", "none"),
                new MetricsRegistry(), health);

        Response response = middleware.handle(request("/ready"), ignored -> Response.text("ok"));
        assertEquals(503, response.status());
    }

    private static Request request(String path) {
        return new Request(HttpMethod.GET, path, Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static String body(Response response) {
        return new String(response.body(), StandardCharsets.UTF_8);
    }

    private static final class TestConfig implements Config {
        @Override public Optional<String> getString(String key) { return Optional.empty(); }
        @Override public Optional<Integer> getInt(String key) { return Optional.empty(); }
        @Override public Optional<Boolean> getBoolean(String key) { return Optional.empty(); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }
}

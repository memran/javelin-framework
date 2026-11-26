package io.javelin.observability;

import io.javelin.core.Application;
import io.javelin.core.HttpMethod;
import io.javelin.core.Json;
import io.javelin.core.JsonResponse;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/** Built-in health, readiness and metrics endpoints. */
public final class ObservabilityMiddleware implements Middleware {
    private final Application application;
    private final ObservabilityConfig config;
    private final MetricsRegistry metrics;
    private final HealthRegistry health;

    public ObservabilityMiddleware(Application application, ObservabilityConfig config,
                                   MetricsRegistry metrics, HealthRegistry health) {
        this.application = application;
        this.config = config;
        this.metrics = metrics;
        this.health = health;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        metrics.increment("http.requests");
        if (request.method() == HttpMethod.GET) {
            if (request.path().equals(config.healthPath())) {
                return healthResponse();
            }
            if (request.path().equals(config.readyPath())) {
                return readinessResponse();
            }
            if (request.path().equals(config.metricsPath())) {
                return metricsResponse(request);
            }
        }
        Response response = next.handle(request);
        metrics.increment("http.response." + response.status());
        return response;
    }

    private Response healthResponse() {
        Map<String, Health> checks = health.liveness();
        boolean up = checks.values().stream().allMatch(Health::healthy);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", up ? Health.UP : Health.DOWN);
        body.put("checks", checks);
        return JsonResponse.of(up ? 200 : 503, body);
    }

    private Response readinessResponse() {
        Map<String, Health> checks = health.readiness();
        boolean ready = checks.values().stream().allMatch(Health::healthy);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", ready ? "ready" : "not ready");
        body.put("checks", checks);
        return JsonResponse.of(ready ? 200 : 503, body);
    }

    private Response metricsResponse(Request request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("metrics", metrics.snapshot());
        body.put("runtime", RuntimeSnapshot.capture().toMap());
        body.put("startup", StartupProfile.snapshot());
        Map<String, Object> pools = new LinkedHashMap<>();
        DbPoolStats.collect(application).forEach(pool -> pools.put(pool.name(), pool.toMap()));
        body.put("pools", pools);

        boolean plainText = request.header("Accept")
                .map(value -> value.contains("text/plain"))
                .orElse(false);
        if (plainText) {
            String text = PrometheusText.render(cast(body.get("metrics")), metrics.counterNames(),
                    cast(body.get("runtime")), cast(body.get("startup")), cast(body.get("pools")));
            return new Response(200,
                    Map.of("Content-Type", "text/plain; version=0.0.4; charset=utf-8"),
                    text.getBytes(StandardCharsets.UTF_8));
        }
        return Json.ok(body);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> cast(Object value) {
        return value instanceof Map<?, ?> map ? (Map<String, Object>) map : Map.of();
    }
}

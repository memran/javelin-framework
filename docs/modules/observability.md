# Observability Module

Artifact: `io.javelin:javelin-observability`

`javelin-observability` adds built-in health, readiness and metrics endpoints plus a small metrics registry and tracing contract.

## Endpoints

Registered automatically by `ObservabilityServiceProvider`:

| Path | Response |
|---|---|
| `GET /health` | liveness status and checks; `503` when a check is down |
| `GET /ready` | readiness status; `503` when not ready |
| `GET /metrics` | JSON metrics, runtime, startup timings and DB pool stats |

With `Accept: text/plain`, `/metrics` returns a Prometheus-style text view. Paths are configurable:

```yaml
observability:
  enabled: true
  health_path: /health
  ready_path: /ready
  metrics_path: /metrics
  tracing: none      # none | log | otel
```

## Metrics

```java
import io.javelin.observability.Observability;

Observability.metrics().increment("emails.sent");
Observability.metrics().gauge("queue.depth", 12);
```

Every request increments `http.requests` and `http.response.<status>`. The metrics endpoint also reports JVM heap/non-heap, thread counts, virtual-thread scheduler parallelism, startup milestones and HikariCP active/idle/max connections per configured database.

## Health checks

```java
Observability.health().live("custom", () -> Health.up(Map.of("detail", 1)));
Observability.health().ready("database", () -> {
    try {
        app.make(Database.class).table("javelin_migrations").first();
        return Health.up();
    } catch (RuntimeException exception) {
        return Health.down("database unreachable");
    }
});
```

Built-in liveness checks cover heap usage and thread count; a default readiness check reports the application as up.

## Tracing

`Tracer` is a small span contract:

```java
Observability.tracer().span("import.users", () -> importer.run());
```

- `none` — no-op tracer (default),
- `log` — logs span durations through the structured logger,
- `otel` — uses OpenTelemetry's global tracer when `opentelemetry-api` is present.

The OTel API dependency is optional, so applications that do not use tracing do not pay for it. Javelin does **not** install an OTel SDK or exporter: `tracing: otel` alone records spans into `GlobalOpenTelemetry` but exports nothing. Applications must add `opentelemetry-sdk` plus an exporter (for example OTLP or Jaeger) and initialize `GlobalOpenTelemetry` during their own bootstrap, before Javelin providers boot. Without that setup, use `tracing: log` to see span durations in the structured logs.

The demo app wires this in `app/providers/TracingProvider.java`. Set `TRACING_DRIVER=otel` and `OTEL_EXPORTER_OTLP_ENDPOINT` (for example `http://localhost:4317`) to export spans over OTLP/gRPC, then call `GET /demo/tracing`. With no endpoint configured the adapter stays inactive and local runs need no collector.

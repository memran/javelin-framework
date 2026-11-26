package io.javelin.observability;

import java.util.Objects;

/** Static access to the active observability components. */
public final class Observability {
    private static volatile MetricsRegistry metrics;
    private static volatile HealthRegistry health;
    private static volatile Tracer tracer = new NoopTracer();

    private Observability() {
    }

    public static void use(MetricsRegistry metricsRegistry, HealthRegistry healthRegistry, Tracer activeTracer) {
        metrics = Objects.requireNonNull(metricsRegistry, "metrics");
        health = Objects.requireNonNull(healthRegistry, "health");
        tracer = Objects.requireNonNull(activeTracer, "tracer");
    }

    public static MetricsRegistry metrics() {
        return metrics;
    }

    public static HealthRegistry health() {
        return health;
    }

    public static Tracer tracer() {
        return tracer;
    }
}

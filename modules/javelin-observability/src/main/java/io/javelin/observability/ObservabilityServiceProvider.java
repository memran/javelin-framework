package io.javelin.observability;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.StructuredLogger;

import java.util.Map;

/** Registers health, readiness and metrics endpoints plus the configured tracer. */
public final class ObservabilityServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(ObservabilityConfig.class, () -> ObservabilityConfig.from(app.config()));
        app.singleton(MetricsRegistry.class, MetricsRegistry::new);
        app.singleton(HealthRegistry.class, HealthRegistry::new);
        app.singleton(Tracer.class, () -> tracer(app));
        app.singleton(ObservabilityMiddleware.class, () -> new ObservabilityMiddleware(
                app.make(Application.class),
                app.make(ObservabilityConfig.class),
                app.make(MetricsRegistry.class),
                app.make(HealthRegistry.class)));
    }

    private Tracer tracer(Application app) {
        String mode = app.make(ObservabilityConfig.class).tracing();
        if ("otel".equalsIgnoreCase(mode)) {
            return new OpenTelemetryTracer();
        }
        if ("log".equalsIgnoreCase(mode) && app.has(StructuredLogger.class)) {
            return new LoggingTracer(app.make(StructuredLogger.class));
        }
        return new NoopTracer();
    }

    /** The kernel applies middleware only for resolved routes; add a no-op route when needed. */
    private static void ensureRoute(Application app, String path) {
        boolean exists = app.router().routes().stream()
                .anyMatch(route -> route.method() == io.javelin.core.HttpMethod.GET
                        && route.path().equals(path));
        if (!exists) {
            app.router().get(path, request -> io.javelin.core.Response.noContent());
        }
    }

    @Override
    public void boot(Application app) {
        MetricsRegistry metrics = app.make(MetricsRegistry.class);
        HealthRegistry health = app.make(HealthRegistry.class);
        Observability.use(metrics, health, app.make(Tracer.class));

        health.live("memory", () -> {
            Runtime runtime = Runtime.getRuntime();
            long used = runtime.totalMemory() - runtime.freeMemory();
            return used >= runtime.maxMemory() * 0.95
                    ? Health.down("heap usage above 95%")
                    : Health.up(Map.of("heap_used_mb", used / (1024 * 1024)));
        });
        health.live("threads", () -> Thread.activeCount() > 10_000
                ? Health.down("too many threads")
                : Health.up(Map.of("threads", Thread.activeCount())));
        health.ready("application", Health::up);

        StartupProfile.stage("boot");
        ObservabilityConfig settings = app.make(ObservabilityConfig.class);
        if (settings.enabled()) {
            ensureRoute(app, settings.healthPath());
            ensureRoute(app, settings.readyPath());
            ensureRoute(app, settings.metricsPath());
            app.router().middleware(app.make(ObservabilityMiddleware.class));
        }
    }
}

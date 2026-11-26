package io.javelin.observability;

import java.util.function.Supplier;

/** Optional OpenTelemetry adapter. Requires an OTel API on the classpath. */
public final class OpenTelemetryTracer implements io.javelin.observability.Tracer {
    private final io.opentelemetry.api.trace.Tracer tracer;

    public OpenTelemetryTracer() {
        this(io.opentelemetry.api.GlobalOpenTelemetry.getTracer("javelin"));
    }

    public OpenTelemetryTracer(io.opentelemetry.api.trace.Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public <T> T span(String name, Supplier<T> work) {
        io.opentelemetry.api.trace.Span span = tracer.spanBuilder(name).startSpan();
        try (io.opentelemetry.context.Scope ignored = span.makeCurrent()) {
            return work.get();
        } catch (Throwable throwable) {
            span.recordException(throwable);
            span.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR);
            throw throwable;
        } finally {
            span.end();
        }
    }
}

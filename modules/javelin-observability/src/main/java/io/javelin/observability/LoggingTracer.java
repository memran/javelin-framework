package io.javelin.observability;

import io.javelin.core.StructuredLogger;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

/** Lightweight tracer that logs span durations through the core logger. */
public final class LoggingTracer implements Tracer {
    private final StructuredLogger logger;

    public LoggingTracer(StructuredLogger logger) {
        this.logger = logger;
    }

    @Override
    public <T> T span(String name, Supplier<T> work) {
        Instant start = Instant.now();
        try {
            return work.get();
        } finally {
            long millis = Duration.between(start, Instant.now()).toMillis();
            logger.info("span " + name, Map.of("span", name, "duration_ms", millis));
        }
    }
}

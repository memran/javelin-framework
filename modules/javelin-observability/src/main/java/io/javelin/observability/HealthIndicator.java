package io.javelin.observability;

/** One liveness or readiness probe. */
@FunctionalInterface
public interface HealthIndicator {
    Health check();
}

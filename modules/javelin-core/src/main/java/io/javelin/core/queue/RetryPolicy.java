package io.javelin.core.queue;

import java.time.Duration;
import java.util.Objects;

/** Delivery limits passed to the queue backend when a handler is registered. */
public record RetryPolicy(int maxAttempts, Duration backoff) {
    public static final RetryPolicy DEFAULT = new RetryPolicy(3, Duration.ofSeconds(1));

    public RetryPolicy {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1");
        }
        backoff = Objects.requireNonNull(backoff, "backoff");
        if (backoff.isNegative()) {
            throw new IllegalArgumentException("backoff must not be negative");
        }
    }
}

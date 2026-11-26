package io.javelin.queue;

import io.javelin.core.Config;

import java.time.Duration;
import java.util.UUID;

/** Settings for the background worker. */
public record QueueWorkerConfig(String workerId, int concurrency, Duration pollInterval, Duration lease,
                                Duration timeout, Duration shutdownTimeout, String backoff,
                                Duration initialBackoff, Duration maxBackoff) {
    public static QueueWorkerConfig from(Config config) {
        return new QueueWorkerConfig(
                config.getString("queue.worker.id", UUID.randomUUID().toString()),
                Math.max(1, config.getInt("queue.worker.concurrency", 1)),
                Duration.ofMillis(config.getLong("queue.worker.poll_interval_ms", 500)),
                Duration.ofSeconds(config.getLong("queue.worker.lease_seconds", 60)),
                Duration.ofSeconds(config.getLong("queue.worker.timeout_seconds", 300)),
                Duration.ofSeconds(config.getLong("queue.worker.shutdown_seconds", 10)),
                config.getString("queue.retry.backoff", "constant"),
                Duration.ofMillis(config.getLong("queue.retry.initial_ms", 1000)),
                Duration.ofMillis(config.getLong("queue.retry.max_ms", 60_000)));
    }

    public Duration backoffFor(int attempt, Duration policyBackoff) {
        if (!"exponential".equalsIgnoreCase(backoff)) {
            return policyBackoff;
        }
        long shift = Math.min(Math.max(attempt - 1, 0), 16);
        long millis = initialBackoff.toMillis() * (1L << shift);
        return Duration.ofMillis(Math.min(millis, maxBackoff.toMillis()));
    }
}

package io.javelin.scheduler;

import io.javelin.core.Config;

import java.time.Duration;
import java.time.ZoneId;
import java.util.UUID;

/** Scheduler settings read from explicit {@code scheduler.*} keys. */
public record SchedulerConfig(boolean enabled, Duration pollInterval, Duration lockTtl,
                              ZoneId zone, String owner, String driver) {
    public static SchedulerConfig from(Config config) {
        return new SchedulerConfig(
                config.getBoolean("scheduler.enabled").orElse(true),
                Duration.ofMillis(config.getLong("scheduler.poll_interval_ms", 1000)),
                Duration.ofSeconds(config.getLong("scheduler.lock_ttl_seconds", 120)),
                ZoneId.of(config.getString("scheduler.timezone", "UTC")),
                config.getString("scheduler.owner", UUID.randomUUID().toString()),
                config.getString("scheduler.driver", "memory"));
    }
}

package io.javelin.cache.caffeine;

import io.javelin.core.Config;

import java.time.Duration;

/** Settings for the Caffeine cache adapter. */
public record CaffeineCacheConfig(long maximumSize, Duration defaultTtl) {
    public static CaffeineCacheConfig from(Config config) {
        long maximumSize = config.getLong("cache.caffeine.maximum_size", 10_000L);
        if (maximumSize < 1) {
            throw new IllegalArgumentException("cache.caffeine.maximum_size must be at least 1");
        }
        return new CaffeineCacheConfig(maximumSize,
                Duration.ofSeconds(config.getLong("cache.caffeine.default_ttl_seconds", 0)));
    }
}

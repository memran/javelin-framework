package io.javelin.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import io.javelin.cache.Cache;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** Caffeine-backed cache with per-entry TTL from each put call. */
public final class CaffeineCache implements Cache {
    private final com.github.benmanes.caffeine.cache.Cache<String, Entry> cache;

    public CaffeineCache(long maximumSize) {
        this(maximumSize, Duration.ZERO);
    }

    public CaffeineCache(long maximumSize, Duration defaultTtl) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder().maximumSize(maximumSize);
        if (!defaultTtl.isZero() && !defaultTtl.isNegative()) {
            builder.expireAfterWrite(defaultTtl);
        }
        this.cache = builder.expireAfter(new Expiry<String, Entry>() {
            @Override
            public long expireAfterCreate(String key, Entry value, long currentTime) {
                return value.ttlNanos();
            }

            @Override
            public long expireAfterUpdate(String key, Entry value, long currentTime, long currentDuration) {
                return value.ttlNanos();
            }

            @Override
            public long expireAfterRead(String key, Entry value, long currentTime, long currentDuration) {
                return value.ttlNanos();
            }
        }).build();
    }

    public void put(String key, Object value, Duration ttl) {
        cache.put(key, new Entry(value, ttl.toNanos()));
    }

    @Override
    public Optional<Object> get(String key) {
        Entry entry = cache.getIfPresent(key);
        return Optional.ofNullable(entry).map(Entry::value);
    }

    @Override
    public void forget(String key) {
        cache.invalidate(key);
    }

    /** Explicit invalidation of every entry. */
    public void invalidateAll() {
        cache.invalidateAll();
    }

    public long size() {
        return cache.estimatedSize();
    }

    private record Entry(Object value, long ttlNanos) {
        private Entry {
            if (ttlNanos <= 0) {
                throw new IllegalArgumentException("TTL must be positive");
            }
            java.util.Objects.requireNonNull(value, "value");
        }
    }
}

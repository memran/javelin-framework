package io.javelin.cache;

import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryCache implements Cache {
    private final Map<String, Entry> values = new ConcurrentHashMap<>();
    private final Clock clock;

    public InMemoryCache() {
        this(Clock.systemUTC());
    }

    InMemoryCache(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void put(String key, Object value, Duration ttl) {
        values.put(key, new Entry(value, clock.millis() + ttl.toMillis()));
    }

    @Override
    public Optional<Object> get(String key) {
        Entry entry = values.get(key);
        if (entry == null) {
            return Optional.empty();
        }
        if (clock.millis() >= entry.expiresAt()) {
            values.remove(key);
            return Optional.empty();
        }
        return Optional.ofNullable(entry.value());
    }

    @Override
    public void forget(String key) {
        values.remove(key);
    }

    private record Entry(Object value, long expiresAt) {
    }
}

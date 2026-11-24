package io.javelin.cache;

import io.javelin.core.Json;

import java.time.Duration;
import java.util.Optional;

public interface Cache {
    void put(String key, Object value, Duration ttl);

    Optional<Object> get(String key);

    void forget(String key);

    /** Converts a cached JSON-backed value to the requested type. */
    default <T> Optional<T> get(String key, Class<T> type) {
        return get(key).map(value -> Json.convert(value, type));
    }
}

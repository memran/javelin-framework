package io.javelin.cache;

import java.time.Duration;
import java.util.Optional;

public interface Cache {
    void put(String key, Object value, Duration ttl);

    Optional<Object> get(String key);

    void forget(String key);
}

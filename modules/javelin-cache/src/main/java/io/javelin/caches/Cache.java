package io.javelin.caches;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * Static facade for the active {@link io.javelin.cache.Cache} binding.
 *
 * <p>Import this class when you want the {@code Cache.put(...)} style API;
 * import {@code io.javelin.cache.Cache} when you need the contract or injection.
 */
public final class Cache {
    private static volatile io.javelin.cache.Cache active = new io.javelin.cache.InMemoryCache();

    private Cache() {
    }

    public static void use(io.javelin.cache.Cache cache) {
        active = Objects.requireNonNull(cache, "cache");
    }

    public static io.javelin.cache.Cache active() {
        return active;
    }

    public static void put(String key, Object value, Duration ttl) {
        active.put(key, value, ttl);
    }

    public static Optional<Object> get(String key) {
        return active.get(key);
    }

    public static void forget(String key) {
        active.forget(key);
    }
}

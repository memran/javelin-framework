package io.javelin.cache.caffeine;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CaffeineCacheTest {
    @Test
    void storesAndInvalidatesEntries() {
        CaffeineCache cache = new CaffeineCache(100);
        cache.put("users_count", 100, Duration.ofMinutes(5));

        assertEquals(100, cache.get("users_count").orElseThrow());
        cache.forget("users_count");
        assertTrue(cache.get("users_count").isEmpty());
    }

    @Test
    void expiresEntriesAfterPerEntryTtl() throws Exception {
        CaffeineCache cache = new CaffeineCache(100);
        cache.put("short", "value", Duration.ofMillis(80));
        Thread.sleep(200);

        assertTrue(cache.get("short").isEmpty());
    }

    @Test
    void staticFacadeUsesActiveCache() {
        io.javelin.cache.Cache previous = io.javelin.caches.Cache.active();
        try {
            CaffeineCache cache = new CaffeineCache(10);
            io.javelin.caches.Cache.use(cache);
            io.javelin.caches.Cache.put("users_count", 100, Duration.ofMinutes(5));

            assertEquals(100, io.javelin.caches.Cache.get("users_count").orElseThrow());
            io.javelin.caches.Cache.forget("users_count");
            assertTrue(io.javelin.caches.Cache.get("users_count").isEmpty());
            assertEquals(0, cache.size());
        } finally {
            io.javelin.caches.Cache.use(previous);
        }
    }
}

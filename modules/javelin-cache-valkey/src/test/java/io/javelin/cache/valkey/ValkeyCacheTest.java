package io.javelin.cache.valkey;

import io.javelin.cache.Cache;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ValkeyCacheTest {
    @Test
    void encodesAndDecodesJsonValues() {
        String encoded = ValkeyCache.encode(Map.of("name", "javelin", "count", 2));

        Object decoded = ValkeyCache.decode(encoded);

        assertTrue(decoded instanceof Map);
        assertEquals("javelin", ((Map<?, ?>) decoded).get("name"));
        assertEquals(2, ((Map<?, ?>) decoded).get("count"));
    }

    @Test
    void typedCacheAccessConvertsJsonBackToTheRequestedType() {
        String json = ValkeyCache.encode(Map.of("name", "Ada", "roles", List.of("admin")));
        Cache cache = new Cache() {
            @Override
            public void put(String key, Object value, Duration ttl) {
            }

            @Override
            public Optional<Object> get(String key) {
                return Optional.ofNullable(ValkeyCache.decode(json));
            }

            @Override
            public void forget(String key) {
            }
        };

        User user = cache.get("user:1", User.class).orElseThrow();

        assertEquals("Ada", user.name());
        assertEquals(List.of("admin"), user.roles());
    }

    record User(String name, List<String> roles) {
    }
}

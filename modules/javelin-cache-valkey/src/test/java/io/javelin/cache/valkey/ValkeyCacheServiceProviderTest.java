package io.javelin.cache.valkey;

import io.javelin.cache.Cache;
import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Env;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ValkeyCacheServiceProviderTest {
    @Test
    void bindsValkeyOnlyWhenSelected() {
        Application app = application("valkey");
        new ValkeyCacheServiceProvider().register(app);
        assertTrue(app.has(Cache.class));
        assertTrue(app.make(Cache.class) instanceof ValkeyCache);

        Application memory = application("memory");
        new ValkeyCacheServiceProvider().register(memory);
        assertFalse(memory.has(Cache.class));
    }

    private static Application application(String driver) {
        Config config = new Config() {
            @Override
            public Optional<String> getString(String key) {
                return Map.of("cache.driver", driver, "cache.url", "redis://valkey:6379").entrySet().stream()
                        .filter(entry -> entry.getKey().equals(key))
                        .map(Map.Entry::getValue)
                        .findFirst();
            }

            @Override
            public Optional<Integer> getInt(String key) {
                return Optional.empty();
            }

            @Override
            public Optional<Boolean> getBoolean(String key) {
                return Optional.empty();
            }

            @Override
            public List<String> getStringList(String key) {
                return List.of();
            }
        };
        return new Application(config, key -> Optional.empty());
    }
}

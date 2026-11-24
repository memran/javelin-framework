package io.javelin.http.common;

import io.javelin.core.Config;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class HttpTimeoutConfigTest {
    @Test
    void defaultsAre30And60Seconds() {
        assertEquals(Duration.ofSeconds(30), HttpTimeoutConfig.DEFAULT.request());
        assertEquals(Duration.ofSeconds(60), HttpTimeoutConfig.DEFAULT.idle());
    }

    @Test
    void rejectsNonPositiveDurations() {
        assertThrows(IllegalArgumentException.class,
                () -> new HttpTimeoutConfig(Duration.ZERO, Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new HttpTimeoutConfig(Duration.ofSeconds(1), Duration.ofSeconds(-1)));
    }

    @Test
    void readsTimeoutKeysFromConfig() {
        Config config = new Config() {
            @Override
            public Optional<String> getString(String key) {
                return Optional.ofNullable(Map.of(
                        "server.host", "0.0.0.0",
                        "server.request_timeout_seconds", "5",
                        "server.idle_timeout_seconds", "15"
                ).get(key));
            }

            @Override
            public Optional<Integer> getInt(String key) {
                return getString(key).map(Integer::parseInt);
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

        HttpAdapterConfig adapter = HttpAdapterConfig.from(config, java.nio.file.Path.of("."));

        assertEquals(Duration.ofSeconds(5), adapter.timeouts().request());
        assertEquals(Duration.ofSeconds(15), adapter.timeouts().idle());
    }
}

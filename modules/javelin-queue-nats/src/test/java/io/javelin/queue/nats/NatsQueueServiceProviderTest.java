package io.javelin.queue.nats;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.queue.Queue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class NatsQueueServiceProviderTest {
    @Test
    void bindsNatsQueueOnlyWhenSelected() {
        Application nats = application("nats");
        new NatsQueueServiceProvider().register(nats);
        // The singleton is resolved lazily, so no server connection is made here.
        assertTrue(nats.has(Queue.class));

        Application none = application("none");
        new NatsQueueServiceProvider().register(none);
        assertFalse(none.has(Queue.class));
    }

    private static Application application(String driver) {
        Config config = new Config() {
            @Override
            public Optional<String> getString(String key) {
                return Map.of(
                        "queue.driver", driver,
                        "queue.url", "nats://nats:4222",
                        "queue.stream", "JAVELIN",
                        "queue.subject_prefix", "javelin"
                ).entrySet().stream()
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

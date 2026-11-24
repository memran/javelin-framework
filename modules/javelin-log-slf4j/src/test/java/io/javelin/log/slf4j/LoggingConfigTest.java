package io.javelin.log.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.javelin.core.Config;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LoggingConfigTest {
    @Test
    void readsExplicitLogKeys() {
        Config config = new MapConfig(Map.of(
                "log.level.root", "warn",
                "log.level.io.netty", "error",
                "log.request_logging", "false",
                "log.request_id_header", "X-Trace"));

        LoggingConfig logging = LoggingConfig.from(config);

        assertFalse(logging.requestLogging());
        assertEquals("X-Trace", logging.requestIdHeader());
        assertEquals("X-Correlation-ID", logging.correlationIdHeader());
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        var root = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        var netty = context.getLogger("io.netty");
        Level oldRoot = root.getLevel();
        Level oldNetty = netty.getLevel();
        try {
            logging.applyLevels();
            assertEquals(Level.WARN, root.getLevel());
            assertEquals(Level.ERROR, netty.getLevel());
        } finally {
            root.setLevel(oldRoot);
            netty.setLevel(oldNetty);
        }
    }

    @Test
    void defaultsAreDocumentedValues() {
        LoggingConfig logging = LoggingConfig.from(new MapConfig(Map.of()));
        assertTrue(logging.requestLogging());
        assertEquals("X-Request-ID", logging.requestIdHeader());
        assertEquals("X-Correlation-ID", logging.correlationIdHeader());
    }

    @Test
    void rejectsInvalidLevels() {
        LoggingConfig logging = LoggingConfig.from(new MapConfig(Map.of("log.level.root", "verbose")));
        assertThrows(IllegalArgumentException.class, logging::applyLevels);
    }

    private record MapConfig(Map<String, String> values) implements Config {
        @Override
        public Set<String> keys() {
            return Set.copyOf(values.keySet());
        }

        @Override
        public Optional<String> getString(String key) {
            return Optional.ofNullable(values.get(key));
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return getString(key).map(Integer::parseInt);
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return getString(key).map(Boolean::parseBoolean);
        }

        @Override
        public List<String> getStringList(String key) {
            return List.of();
        }
    }
}

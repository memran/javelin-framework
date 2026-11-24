package io.javelin.db.jdbc;

import io.javelin.core.StructuredLogger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SqlTimerTest {
    @Test
    void logsQueriesAtOrAboveTheThreshold() {
        RecordingLogger logger = new RecordingLogger();
        SqlTimer timer = new SqlTimer(logger, new QueryTimingConfig(true, 0, false));

        String result = timer.measure("select * from users where id = ?", () -> "ok");

        assertEquals("ok", result);
        assertEquals(1, logger.warnings.size());
        assertTrue(logger.warnings.get(0).contains("Slow query"), logger.warnings.get(0));
        assertEquals("select * from users where id = ?", logger.contexts.get(0).get("sql"));
        assertTrue(logger.contexts.get(0).containsKey("duration_ms"));
    }

    @Test
    void disabledTimerDoesNotLog() {
        RecordingLogger logger = new RecordingLogger();
        SqlTimer timer = SqlTimer.disabled();

        timer.measure("select 1", () -> null);

        assertTrue(logger.warnings.isEmpty());
        assertTrue(logger.debugs.isEmpty());
    }

    @Test
    void logAllEmitsDebugForEveryQuery() {
        RecordingLogger logger = new RecordingLogger();
        SqlTimer timer = new SqlTimer(logger, new QueryTimingConfig(true, Long.MAX_VALUE, true));

        timer.measure("delete from sessions", () -> 1);

        assertEquals(1, logger.debugs.size());
        assertTrue(logger.debugs.get(0).contains("delete from sessions"));
        assertTrue(logger.warnings.isEmpty());
    }

    private static final class RecordingLogger implements StructuredLogger {
        private final List<String> warnings = new ArrayList<>();
        private final List<String> debugs = new ArrayList<>();
        private final List<Map<String, ?>> contexts = new ArrayList<>();

        @Override public void debug(String message) { debugs.add(message); }
        @Override public void info(String message) { }
        @Override public void warn(String message) { warnings.add(message); }
        @Override public void error(String message, Throwable throwable) { }
        @Override public void info(String message, Map<String, ?> context) { }
        @Override public void warn(String message, Map<String, ?> context) {
            warnings.add(message);
            contexts.add(context);
        }
        @Override public void error(String message, Throwable throwable, Map<String, ?> context) { }
    }
}

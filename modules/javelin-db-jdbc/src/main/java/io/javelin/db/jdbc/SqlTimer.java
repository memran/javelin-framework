package io.javelin.db.jdbc;

import io.javelin.core.Logger;
import io.javelin.core.StructuredLogger;

import java.util.Map;
import java.util.function.Supplier;

/** Times SQL execution and logs slow queries through the core logger. */
public final class SqlTimer {
    private static final int MAX_SQL_LENGTH = 500;

    private final Logger logger;
    private final QueryTimingConfig config;

    public SqlTimer(Logger logger, QueryTimingConfig config) {
        this.logger = logger;
        this.config = config;
    }

    public static SqlTimer disabled() {
        return new SqlTimer(null, QueryTimingConfig.disabled());
    }

    public <T> T measure(String sql, Supplier<T> action) {
        if (!config.enabled()) {
            return action.get();
        }
        long start = System.nanoTime();
        try {
            return action.get();
        } finally {
            long millis = (System.nanoTime() - start) / 1_000_000;
            String summary = summarize(sql);
            if (config.logAll()) {
                if (logger instanceof StructuredLogger structured) {
                    structured.debug("Query " + millis + "ms: " + summary);
                } else if (logger != null) {
                    logger.info("Query " + millis + "ms: " + summary);
                }
            }
            if (millis >= config.slowThresholdMs()) {
                if (logger instanceof StructuredLogger structured) {
                    structured.warn("Slow query took " + millis + "ms",
                            Map.of("sql", summary, "duration_ms", millis));
                } else if (logger != null) {
                    logger.warn("Slow query took " + millis + "ms: " + summary);
                }
            }
        }
    }

    private static String summarize(String sql) {
        String normalized = sql == null ? "" : sql.replaceAll("\\s+", " ").trim();
        return normalized.length() <= MAX_SQL_LENGTH
                ? normalized
                : normalized.substring(0, MAX_SQL_LENGTH) + "...";
    }
}

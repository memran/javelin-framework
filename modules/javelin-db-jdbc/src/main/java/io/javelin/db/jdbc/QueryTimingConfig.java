package io.javelin.db.jdbc;

import io.javelin.core.Config;

/** Query timing settings read from explicit {@code database.query.*} keys. */
public record QueryTimingConfig(boolean enabled, long slowThresholdMs, boolean logAll) {
    public static final long DEFAULT_SLOW_MS = 100;

    public static QueryTimingConfig from(Config config) {
        return new QueryTimingConfig(
                config.getBoolean("database.query.timing").orElse(true),
                config.getLong("database.query.slow_ms", DEFAULT_SLOW_MS),
                config.getBoolean("database.query.log_all").orElse(false));
    }

    public static QueryTimingConfig disabled() {
        return new QueryTimingConfig(false, Long.MAX_VALUE, false);
    }
}

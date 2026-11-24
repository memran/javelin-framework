package io.javelin.log.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.javelin.core.Config;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/** Logging settings read from explicit {@code log.*} config keys. */
public final class LoggingConfig {
    private static final String LEVEL_PREFIX = "log.level.";

    private final String rootLevel;
    private final Map<String, String> levels;
    private final boolean requestLogging;
    private final String requestIdHeader;
    private final String correlationIdHeader;

    public LoggingConfig(String rootLevel, Map<String, String> levels, boolean requestLogging,
                         String requestIdHeader, String correlationIdHeader) {
        this.rootLevel = rootLevel;
        this.levels = Map.copyOf(levels);
        this.requestLogging = requestLogging;
        this.requestIdHeader = requestIdHeader;
        this.correlationIdHeader = correlationIdHeader;
    }

    public static LoggingConfig from(Config config) {
        String root = config.getString(LEVEL_PREFIX + "root", "INFO");
        Map<String, String> levels = new LinkedHashMap<>();
        for (String key : config.keys()) {
            if (key.startsWith(LEVEL_PREFIX) && !key.equals(LEVEL_PREFIX + "root")) {
                config.getString(key).ifPresent(value -> levels.put(key.substring(LEVEL_PREFIX.length()), value));
            }
        }
        return new LoggingConfig(
                root,
                levels,
                config.getBoolean("log.request_logging").orElse(true),
                config.getString("log.request_id_header", "X-Request-ID"),
                config.getString("log.correlation_id_header", "X-Correlation-ID"));
    }

    /** Applies levels to the active Logback context. Never writes logback.xml. */
    public void applyLevels() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(parse(rootLevel, "root"));
        for (Map.Entry<String, String> entry : levels.entrySet()) {
            context.getLogger(entry.getKey()).setLevel(parse(entry.getValue(), entry.getKey()));
        }
    }

    public boolean requestLogging() {
        return requestLogging;
    }

    public String requestIdHeader() {
        return requestIdHeader;
    }

    public String correlationIdHeader() {
        return correlationIdHeader;
    }

    private static final Set<String> VALID_LEVELS = Set.of(
            "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF", "ALL");

    private static Level parse(String value, String logger) {
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        if (!VALID_LEVELS.contains(normalized)) {
            throw new IllegalArgumentException("Invalid log level '" + value + "' for " + logger);
        }
        return Level.valueOf(normalized);
    }
}

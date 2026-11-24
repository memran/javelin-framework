package io.javelin.log.slf4j;

import io.javelin.core.StructuredLogger;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/** Adapts the core structured logging contract to SLF4J. */
public final class Slf4jLogger implements StructuredLogger {
    private final org.slf4j.Logger logger;

    public Slf4jLogger() {
        this(org.slf4j.LoggerFactory.getLogger("javelin"));
    }

    public Slf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    @Override
    public void info(String message, Map<String, ?> context) {
        withContext(context, () -> logger.info(message));
    }

    @Override
    public void warn(String message, Map<String, ?> context) {
        withContext(context, () -> logger.warn(message));
    }

    @Override
    public void error(String message, Throwable throwable, Map<String, ?> context) {
        withContext(context, () -> logger.error(message, throwable));
    }

    private static void withContext(Map<String, ?> context, Runnable action) {
        if (context == null || context.isEmpty()) {
            action.run();
            return;
        }
        Map<String, String> previous = new HashMap<>();
        try {
            for (Map.Entry<String, ?> entry : context.entrySet()) {
                String key = entry.getKey();
                previous.put(key, MDC.get(key));
                MDC.put(key, String.valueOf(entry.getValue()));
            }
            action.run();
        } finally {
            for (Map.Entry<String, String> entry : previous.entrySet()) {
                if (entry.getValue() == null) {
                    MDC.remove(entry.getKey());
                } else {
                    MDC.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}

package io.javelin.core;

import java.util.Map;

/**
 * Optional structured extension of {@link Logger}.
 *
 * <p>Implementations may put the supplied context onto the SLF4J MDC for the
 * duration of a call, so appenders can render it as fields. The plain
 * {@link Logger} contract is unchanged for existing implementations.
 */
public interface StructuredLogger extends Logger {
    void debug(String message);

    void info(String message, Map<String, ?> context);

    void warn(String message, Map<String, ?> context);

    void error(String message, Throwable throwable, Map<String, ?> context);
}

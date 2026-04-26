package io.javelin.core;

public final class Log {
    private static volatile Logger logger = new NoopLogger();

    private Log() {
    }

    public static void use(Logger logger) {
        Log.logger = logger;
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    private static final class NoopLogger implements Logger {
        @Override
        public void info(String message) {
        }

        @Override
        public void warn(String message) {
        }

        @Override
        public void error(String message, Throwable throwable) {
        }
    }
}

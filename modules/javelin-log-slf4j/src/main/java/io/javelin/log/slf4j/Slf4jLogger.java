package io.javelin.log.slf4j;

import io.javelin.core.Logger;

public final class Slf4jLogger implements Logger {
    private final org.slf4j.Logger logger;

    public Slf4jLogger() {
        this(org.slf4j.LoggerFactory.getLogger("javelin"));
    }

    public Slf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
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
}

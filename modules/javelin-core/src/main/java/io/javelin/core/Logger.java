package io.javelin.core;

public interface Logger {
    void info(String message);

    void warn(String message);

    void error(String message, Throwable throwable);
}

package io.javelin.core;

public interface HttpServerAdapter extends AutoCloseable {
    void start();

    void stop(int delaySeconds);

    @Override
    default void close() {
        stop(0);
    }
}

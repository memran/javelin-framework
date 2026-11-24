package io.javelin.core.queue;

/** Handles one delivered job. Throw to trigger a retry according to the RetryPolicy. */
@FunctionalInterface
public interface JobHandler {
    void handle(Job job) throws Exception;
}

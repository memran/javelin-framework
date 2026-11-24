package io.javelin.core.queue;

/** Explicit queue contract. Implementations provide publish/subscribe over a concrete backend. */
public interface Queue extends AutoCloseable {
    void publish(Job job);

    void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy);

    @Override
    void close();
}

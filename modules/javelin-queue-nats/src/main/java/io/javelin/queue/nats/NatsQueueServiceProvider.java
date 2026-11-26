package io.javelin.queue.nats;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.queue.Queue;
import io.javelin.queue.JobQueue;

/** Binds a JetStream queue when {@code queue.driver} is {@code nats}. */
public final class NatsQueueServiceProvider implements ServiceProvider {
    public static final String DRIVER = "nats";

    @Override
    public void register(Application app) {
        String driver = app.config().getString("queue.driver").orElse("none");
        if (!DRIVER.equalsIgnoreCase(driver)) {
            return;
        }
        String url = app.config().getString("queue.url").orElse("nats://localhost:4222");
        String stream = app.config().getString("queue.stream").orElse("JAVELIN");
        String prefix = app.config().getString("queue.subject_prefix").orElse("javelin");
        app.singleton(NatsQueue.class, () -> new NatsQueue(url, stream, prefix));
        app.singleton(Queue.class, () -> app.make(NatsQueue.class));
        app.singleton(JobQueue.class, () -> app.make(NatsQueue.class));
    }
}

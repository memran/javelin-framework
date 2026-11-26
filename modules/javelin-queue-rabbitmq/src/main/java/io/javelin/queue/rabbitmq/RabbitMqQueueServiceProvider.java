package io.javelin.queue.rabbitmq;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.queue.JobQueue;

/** Binds the RabbitMQ queue when {@code queue.driver} is {@code rabbitmq}. */
public final class RabbitMqQueueServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String driver = app.config().getString("queue.driver", "none");
        if (!"rabbitmq".equalsIgnoreCase(driver)) {
            return;
        }
        String uri = app.config().getString("queue.rabbitmq.uri", "amqp://guest:guest@localhost:5672/");
        app.singleton(RabbitMqQueue.class, () -> new RabbitMqQueue(uri));
        app.singleton(JobQueue.class, () -> app.make(RabbitMqQueue.class));
    }
}

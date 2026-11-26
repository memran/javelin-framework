package io.javelin.queue;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Wires the handler registry, worker and static queue facade. */
public final class QueueServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(JobHandlerRegistry.class, JobHandlerRegistry::new);
        app.singleton(QueueWorkerConfig.class, () -> QueueWorkerConfig.from(app.config()));
        app.singleton(QueueWorker.class, () -> new QueueWorker(
                app.make(JobQueue.class),
                app.make(JobHandlerRegistry.class),
                app.make(QueueWorkerConfig.class)));
    }

    @Override
    public void boot(Application app) {
        if (!app.has(JobQueue.class)) {
            return;
        }
        io.javelin.queues.Queue.use(app.make(JobQueue.class));
        if (app.config().getBoolean("queue.worker.enabled").orElse(true)) {
            app.make(QueueWorker.class).start();
        }
    }

    @Override
    public void shutdown(Application app) {
        if (app.has(QueueWorker.class)) {
            app.make(QueueWorker.class).close();
        }
    }
}

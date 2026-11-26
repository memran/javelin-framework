package io.javelin.queue.valkey;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.queue.JobQueue;

/** Binds the Valkey/Redis queue when {@code queue.driver} is {@code valkey} or {@code redis}. */
public final class ValkeyQueueServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String driver = app.config().getString("queue.driver", "none").toLowerCase(java.util.Locale.ROOT);
        if (!"valkey".equals(driver) && !"redis".equals(driver)) {
            return;
        }
        String url = app.config().getString("queue.valkey.url", "redis://localhost:6379");
        app.singleton(ValkeyQueue.class, () -> new ValkeyQueue(url));
        app.singleton(JobQueue.class, () -> app.make(ValkeyQueue.class));
    }
}

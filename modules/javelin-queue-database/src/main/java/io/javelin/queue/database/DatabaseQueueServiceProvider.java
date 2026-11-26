package io.javelin.queue.database;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.queue.JobQueue;

import java.time.Duration;

/** Binds the JDBC-backed queue when {@code queue.driver} is {@code database}. */
public final class DatabaseQueueServiceProvider implements ServiceProvider {
    public static final String DRIVER = "database";

    @Override
    public void register(Application app) {
        String driver = app.config().getString("queue.driver", "none");
        if (!DRIVER.equalsIgnoreCase(driver)) {
            return;
        }
        app.singleton(DatabaseQueue.class, () -> new DatabaseQueue(
                app.make(io.javelin.core.Database.class),
                Duration.ofSeconds(app.config().getLong("queue.database.lease_seconds", 60))));
        app.singleton(JobQueue.class, () -> app.make(DatabaseQueue.class));
    }
}

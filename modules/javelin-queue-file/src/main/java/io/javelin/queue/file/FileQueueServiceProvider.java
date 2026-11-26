package io.javelin.queue.file;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.queue.JobQueue;

import java.nio.file.Path;

/** Binds the file-backed queue when {@code queue.driver} is {@code file}. */
public final class FileQueueServiceProvider implements ServiceProvider {
    public static final String DRIVER = "file";

    @Override
    public void register(Application app) {
        String driver = app.config().getString("queue.driver", "none");
        if (!DRIVER.equalsIgnoreCase(driver)) {
            return;
        }
        app.singleton(FileQueue.class, () -> new FileQueue(
                Path.of(app.config().getString("queue.file.dir", "storage/queues/file"))));
        app.singleton(JobQueue.class, () -> app.make(FileQueue.class));
    }
}

package io.javelin.scheduler;

import io.javelin.core.Application;
import io.javelin.core.Database;
import io.javelin.core.ServiceProvider;

/** Wires the scheduler with explicit history and lock backends. */
public final class SchedulerServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(SchedulerConfig.class, () -> SchedulerConfig.from(app.config()));

        app.singleton(ExecutionHistory.class, () -> {
            String driver = app.config().getString("scheduler.history", "memory");
            if ("database".equalsIgnoreCase(driver) && app.has(Database.class)) {
                return new DatabaseExecutionHistory(app.make(Database.class));
            }
            return new InMemoryExecutionHistory();
        });

        app.singleton(SchedulerLock.class, () -> {
            String driver = app.config().getString("scheduler.lock", "memory");
            if ("database".equalsIgnoreCase(driver) && app.has(Database.class)) {
                return new DatabaseSchedulerLock(app.make(Database.class));
            }
            return new InMemorySchedulerLock();
        });

        app.singleton(Scheduler.class, () -> new Scheduler(
                app.make(ExecutionHistory.class),
                app.make(SchedulerLock.class),
                app.make(SchedulerConfig.class)));
    }

    @Override
    public void boot(Application app) {
        Scheduler scheduler = app.make(Scheduler.class);
        io.javelin.schedules.Schedule.use(scheduler);
        if (app.make(SchedulerConfig.class).enabled()) {
            scheduler.start();
        }
    }

    @Override
    public void shutdown(Application app) {
        if (app.has(Scheduler.class)) {
            app.make(Scheduler.class).close();
        }
    }
}

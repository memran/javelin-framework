package io.javelin.scheduler;

/** One unit of scheduled work. */
@FunctionalInterface
public interface ScheduledTask {
    void run() throws Exception;
}

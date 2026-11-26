package io.javelin.scheduler;

import java.time.Duration;

/** Prevents two application instances from running the same task at once. */
public interface SchedulerLock {
    boolean tryLock(String task, String owner, Duration ttl);

    void release(String task, String owner);
}

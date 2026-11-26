package io.javelin.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Process-local lock; suitable for single-instance deployments. */
public final class InMemorySchedulerLock implements SchedulerLock {
    private final Map<String, Entry> locks = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean tryLock(String task, String owner, Duration ttl) {
        Entry current = locks.get(task);
        if (current != null && current.expiresAt().isAfter(Instant.now()) && !current.owner().equals(owner)) {
            return false;
        }
        locks.put(task, new Entry(owner, Instant.now().plus(ttl)));
        return true;
    }

    @Override
    public synchronized void release(String task, String owner) {
        Entry current = locks.get(task);
        if (current != null && current.owner().equals(owner)) {
            locks.remove(task);
        }
    }

    private record Entry(String owner, Instant expiresAt) {
    }
}

package io.javelin.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** JDK-based scheduler with overlap prevention, locks and execution history. */
public final class Scheduler implements AutoCloseable {
    private final Map<String, TaskDefinition> tasks = new LinkedHashMap<>();
    private final Map<String, Instant> nextRuns = new ConcurrentHashMap<>();
    private final Set<String> running = ConcurrentHashMap.newKeySet();
    private final ExecutionHistory history;
    private final SchedulerLock lock;
    private final SchedulerConfig config;
    private final ScheduledExecutorService ticker = Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable, "javelin-scheduler");
        thread.setDaemon(true);
        return thread;
    });
    private final ExecutorService workers = Executors.newVirtualThreadPerTaskExecutor();

    public Scheduler(ExecutionHistory history, SchedulerLock lock, SchedulerConfig config) {
        this.history = Objects.requireNonNull(history, "history");
        this.lock = Objects.requireNonNull(lock, "lock");
        this.config = Objects.requireNonNull(config, "config");
    }

    public synchronized void register(TaskDefinition definition) {
        Objects.requireNonNull(definition, "definition");
        tasks.put(definition.name(), definition);
        nextRuns.put(definition.name(), definition.schedule().nextRun(Instant.now(), config.zone()));
    }

    public List<TaskDefinition> tasks() {
        return List.copyOf(tasks.values());
    }

    public List<ExecutionRecord> history(int limit) {
        return history.recent(limit);
    }

    public void start() {
        ticker.scheduleAtFixedRate(() -> {
            try {
                runDue();
            } catch (RuntimeException exception) {
                // A failed tick must not cancel subsequent ticks.
            }
        }, 0, config.pollInterval().toMillis(), TimeUnit.MILLISECONDS);
    }

    /** Runs every due task once. Returns the number of tasks started. */
    public int runDue() {
        Instant now = Instant.now();
        List<TaskDefinition> due = new ArrayList<>();
        synchronized (this) {
            for (TaskDefinition definition : tasks.values()) {
                Instant next = nextRuns.get(definition.name());
                if (next != null && !next.isAfter(now)) {
                    due.add(definition);
                }
            }
        }
        int started = 0;
        for (TaskDefinition definition : due) {
            if (start(definition)) {
                started++;
            }
        }
        return started;
    }

    private boolean start(TaskDefinition definition) {
        String name = definition.name();
        if (!definition.allowOverlap() && !running.add(name)) {
            return false;
        }
        String owner = config.owner() + ":" + UUID.randomUUID();
        if (!lock.tryLock(name, owner, config.lockTtl())) {
            running.remove(name);
            return false;
        }
        workers.submit(() -> {
            Instant startedAt = Instant.now();
            boolean success = false;
            String error = "";
            try {
                definition.task().run();
                success = true;
            } catch (Exception exception) {
                error = String.valueOf(exception);
            } finally {
                Instant finishedAt = Instant.now();
                try {
                    history.record(new ExecutionRecord(name, startedAt, finishedAt, success, error));
                } finally {
                    nextRuns.put(name, definition.schedule().nextRun(finishedAt, config.zone()));
                    lock.release(name, owner);
                    running.remove(name);
                }
            }
        });
        return true;
    }

    @Override
    public void close() {
        ticker.shutdownNow();
        workers.shutdown();
        try {
            if (!workers.awaitTermination(config.lockTtl().toMillis(), TimeUnit.MILLISECONDS)) {
                workers.shutdownNow();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            workers.shutdownNow();
        }
    }
}

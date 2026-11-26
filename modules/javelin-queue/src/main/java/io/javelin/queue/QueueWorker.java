package io.javelin.queue;

import io.javelin.core.queue.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/** Background worker with bounded concurrency, timeouts and graceful shutdown. */
public final class QueueWorker implements AutoCloseable {
    private final JobQueue queue;
    private final JobHandlerRegistry registry;
    private final QueueWorkerConfig config;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Semaphore slots;
    private volatile boolean running;
    private Thread loop;

    public QueueWorker(JobQueue queue, JobHandlerRegistry registry, QueueWorkerConfig config) {
        this.queue = queue;
        this.registry = registry;
        this.config = config;
        this.slots = new Semaphore(config.concurrency());
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        loop = Thread.ofPlatform().daemon(true).name("javelin-queue-worker").start(this::loop);
    }

    /** Processes at most the given number of jobs once. */
    public int runOnce(int limit) {
        return process(Math.max(1, limit));
    }

    private void loop() {
        while (running) {
            try {
                if (process(config.concurrency()) == 0) {
                    Thread.sleep(config.pollInterval().toMillis());
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return;
            } catch (RuntimeException exception) {
                // A failed poll must not kill the worker; retry on the next tick.
            }
        }
    }

    private int process(int limit) {
        List<Future<?>> futures = new ArrayList<>();
        for (int index = 0; index < limit; index++) {
            boolean acquired = false;
            try {
                slots.acquire();
                acquired = true;
                Optional<JobQueue.ClaimedJob> claim = queue.claim(config.workerId(), config.lease());
                if (claim.isEmpty()) {
                    slots.release();
                    break;
                }
                JobQueue.ClaimedJob claimed = claim.get();
                futures.add(executor.submit(() -> {
                    try {
                        handle(claimed);
                    } finally {
                        slots.release();
                    }
                }));
            } catch (InterruptedException exception) {
                if (acquired) {
                    slots.release();
                }
                Thread.currentThread().interrupt();
                break;
            } catch (RuntimeException exception) {
                if (acquired) {
                    slots.release();
                }
                break;
            }
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception exception) {
                // handle() records failures; nothing else to do here.
            }
        }
        return futures.size();
    }

    private void handle(JobQueue.ClaimedJob claimed) {
        Job job = claimed.job();
        JobHandlerRegistry.Registration registration = registry.find(job.subject()).orElse(null);
        if (registration == null) {
            queue.fail(claimed.id(), claimed.leaseToken(), "No handler registered for " + job.subject(),
                    java.time.Duration.ZERO);
            return;
        }
        try {
            CompletableFuture.runAsync(() -> {
                try {
                    registration.handler().handle(job);
                } catch (Exception exception) {
                    throw new CompletionException(exception);
                }
            }, executor).orTimeout(config.timeout().toMillis(), TimeUnit.MILLISECONDS).join();
            queue.ack(claimed.id(), claimed.leaseToken());
        } catch (Exception exception) {
            Throwable cause = exception instanceof CompletionException completion
                    ? completion.getCause()
                    : exception;
            int nextAttempt = job.attempts() + 1;
            java.time.Duration delay = config.backoffFor(nextAttempt,
                    registration.retryPolicy().backoffFor(nextAttempt));
            queue.fail(claimed.id(), claimed.leaseToken(), String.valueOf(cause), delay);
        }
    }

    @Override
    public void close() {
        running = false;
        if (loop != null) {
            loop.interrupt();
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(config.shutdownTimeout().toMillis(), TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}

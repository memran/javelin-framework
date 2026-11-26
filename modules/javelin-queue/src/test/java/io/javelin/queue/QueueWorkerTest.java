package io.javelin.queue;

import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class QueueWorkerTest {
    @Test
    void processesJobsAndAcknowledgesThem() {
        InMemoryQueue queue = new InMemoryQueue();
        JobHandlerRegistry registry = new JobHandlerRegistry();
        AtomicInteger handled = new AtomicInteger();
        registry.register("emails.welcome", job -> handled.incrementAndGet(), new RetryPolicy(3, Duration.ZERO));
        queue.dispatch(Job.of("emails.welcome", Map.of("to", "alice")));

        try (QueueWorker worker = worker(queue, registry)) {
            assertEquals(1, worker.runOnce(1));
        }

        assertEquals(1, handled.get());
        assertEquals(0, queue.pendingCount());
    }

    @Test
    void failedHandlersAreRetried() {
        InMemoryQueue queue = new InMemoryQueue();
        JobHandlerRegistry registry = new JobHandlerRegistry();
        registry.register("failing.job", job -> {
            throw new IllegalStateException("boom");
        }, new RetryPolicy(3, Duration.ZERO));
        queue.dispatch(Job.of("failing.job", Map.of()));

        try (QueueWorker worker = worker(queue, registry)) {
            worker.runOnce(1);
        }

        assertEquals(1, queue.pendingCount());
        assertEquals(1, queue.pending.get(0).attempts());
    }

    @Test
    void exponentialBackoffDoublesUntilCap() {
        QueueWorkerConfig config = new QueueWorkerConfig("worker", 1, Duration.ofMillis(10),
                Duration.ofSeconds(30), Duration.ofSeconds(5), Duration.ofSeconds(1),
                "exponential", Duration.ofMillis(100), Duration.ofMillis(500));
        assertEquals(Duration.ofMillis(100), config.backoffFor(1, Duration.ZERO));
        assertEquals(Duration.ofMillis(200), config.backoffFor(2, Duration.ZERO));
        assertEquals(Duration.ofMillis(400), config.backoffFor(3, Duration.ZERO));
        assertEquals(Duration.ofMillis(500), config.backoffFor(10, Duration.ZERO));
    }

    private static QueueWorker worker(InMemoryQueue queue, JobHandlerRegistry registry) {
        QueueWorkerConfig config = new QueueWorkerConfig("worker", 1, Duration.ofMillis(10),
                Duration.ofSeconds(30), Duration.ofSeconds(5), Duration.ofSeconds(1),
                "constant", Duration.ofMillis(1), Duration.ofMillis(10));
        return new QueueWorker(queue, registry, config);
    }

    private static final class InMemoryQueue implements JobQueue {
        private final List<Job> pending = new ArrayList<>();
        private final Map<String, Job> processing = new LinkedHashMap<>();
        private final List<Job> failed = new ArrayList<>();

        @Override public void dispatch(Job job) { pending.add(job); }
        @Override public void later(Job job, Duration delay) { pending.add(job); }
        @Override public Optional<ClaimedJob> claim(String workerId, Duration lease) {
            if (pending.isEmpty()) {
                return Optional.empty();
            }
            Job job = pending.remove(0);
            String token = UUID.randomUUID().toString();
            processing.put(token, job);
            return Optional.of(new ClaimedJob(job.id(), job, token));
        }
        @Override public void ack(String id, String leaseToken) { processing.remove(leaseToken); }
        @Override public void fail(String id, String leaseToken, String error, Duration retryDelay) {
            Job job = processing.remove(leaseToken);
            if (job != null && job.attempts() + 1 >= 3) {
                failed.add(job.withAttempts(job.attempts() + 1));
            } else if (job != null) {
                pending.add(job.withAttempts(job.attempts() + 1));
            }
        }
        @Override public List<Job> failedJobs() { return List.copyOf(failed); }
        @Override public void replay(String failedId) { }
        @Override public long pendingCount() { return pending.size(); }
        @Override public void publish(Job job) { dispatch(job); }
        @Override public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) { }
        @Override public void close() { }
    }
}

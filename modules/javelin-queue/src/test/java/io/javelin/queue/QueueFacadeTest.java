package io.javelin.queue;

import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queues.Queue;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class QueueFacadeTest {
    @Test
    void derivesSubjectFromTypedJobClass() {
        io.javelin.queue.JobQueue previous = Queue.active();
        try {
            CapturingQueue queue = new CapturingQueue();
            Queue.use(queue);
            Queue.dispatch(new SendEmailJob("42"));

            assertEquals("send.email", queue.job.subject());
            assertEquals("{\"userId\":\"42\"}", queue.job.payload());
        } finally {
            if (previous != null) {
                Queue.use(previous);
            }
        }
    }

    record SendEmailJob(String userId) {
    }

    private static final class CapturingQueue implements io.javelin.queue.JobQueue {
        private Job job;

        @Override public void dispatch(Job job) { this.job = job; }
        @Override public void later(Job job, Duration delay) { this.job = job; }
        @Override public Optional<ClaimedJob> claim(String workerId, Duration lease) { return Optional.empty(); }
        @Override public void ack(String id, String leaseToken) { }
        @Override public void fail(String id, String leaseToken, String error, Duration retryDelay) { }
        @Override public List<Job> failedJobs() { return List.of(); }
        @Override public void replay(String failedId) { }
        @Override public long pendingCount() { return 0; }
        @Override public void publish(Job job) { dispatch(job); }
        @Override public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) { }
        @Override public void close() { }
    }
}

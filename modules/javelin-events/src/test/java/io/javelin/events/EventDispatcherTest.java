package io.javelin.events;

import io.javelin.core.queue.Job;
import io.javelin.queues.Queue;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EventDispatcherTest {
    @Test
    void synchronousListenersRunImmediately() {
        DefaultEventDispatcher dispatcher = new DefaultEventDispatcher();
        List<String> seen = new ArrayList<>();
        dispatcher.listen(UserRegistered.class, event -> seen.add(event.userId()));

        dispatcher.dispatch(new UserRegistered("alice"));

        assertEquals(List.of("alice"), seen);
    }

    @Test
    void queuedListenersAreDispatchedThroughTheQueue() {
        DefaultEventDispatcher dispatcher = new DefaultEventDispatcher();
        io.javelin.queue.JobQueue previous = Queue.active();
        List<String> handled = new ArrayList<>();
        try {
            CapturingQueue queue = new CapturingQueue();
            Queue.use(queue);
            dispatcher.listenQueued(UserRegistered.class, event -> handled.add(event.userId()));

            dispatcher.dispatch(new UserRegistered("bob"));
            assertEquals("queued.event", queue.job.subject());

            QueuedEvent envelope = io.javelin.core.Json.parse(queue.job.payload(), QueuedEvent.class);
            dispatcher.dispatchQueued(envelope.eventType(), envelope.payload());
            assertEquals(List.of("bob"), handled);
        } finally {
            if (previous != null) {
                Queue.use(previous);
            }
        }
    }

    record UserRegistered(String userId) {
    }

    private static final class CapturingQueue implements io.javelin.queue.JobQueue {
        private Job job;

        @Override public void dispatch(Job job) { this.job = job; }
        @Override public void later(Job job, Duration delay) { this.job = job; }
        @Override public java.util.Optional<ClaimedJob> claim(String workerId, Duration lease) {
            return java.util.Optional.empty();
        }
        @Override public void ack(String id, String leaseToken) { }
        @Override public void fail(String id, String leaseToken, String error, Duration retryDelay) { }
        @Override public List<Job> failedJobs() { return List.of(); }
        @Override public void replay(String failedId) { }
        @Override public long pendingCount() { return 0; }
        @Override public void publish(Job job) { dispatch(job); }
        @Override public void subscribe(String subject, io.javelin.core.queue.JobHandler handler,
                                        io.javelin.core.queue.RetryPolicy retryPolicy) { }
        @Override public void close() { }
    }
}

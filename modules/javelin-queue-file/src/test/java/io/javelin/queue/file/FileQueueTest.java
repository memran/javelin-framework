package io.javelin.queue.file;

import io.javelin.core.queue.Job;
import io.javelin.queue.JobQueue.ClaimedJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class FileQueueTest {
    @Test
    void dispatchesClaimsAndAcks(@TempDir Path dir) {
        FileQueue queue = new FileQueue(dir);
        Job job = Job.of("emails.welcome", Map.of("to", "alice"));

        queue.dispatch(job);
        assertEquals(1, queue.pendingCount());

        ClaimedJob claimed = queue.claim("worker", Duration.ofSeconds(30)).orElseThrow();
        assertEquals(job.subject(), claimed.job().subject());
        queue.ack(claimed.id(), claimed.leaseToken());
        assertEquals(0, queue.pendingCount());
    }

    @Test
    void delayedJobsAreNotImmediatelyAvailable(@TempDir Path dir) {
        FileQueue queue = new FileQueue(dir);
        queue.later(Job.of("invoices.due", Map.of()), Duration.ofMinutes(5));
        assertEquals(0, queue.pendingCount());
        assertTrue(queue.claim("worker", Duration.ofSeconds(30)).isEmpty());
    }

    @Test
    void failedJobsCanBeReplayed(@TempDir Path dir) {
        FileQueue queue = new FileQueue(dir);
        queue.dispatch(Job.of("failing.job", Map.of()));
        for (int index = 0; index < 3; index++) {
            ClaimedJob claimed = queue.claim("worker", Duration.ofSeconds(30)).orElseThrow();
            queue.fail(claimed.id(), claimed.leaseToken(), "boom", Duration.ZERO);
        }
        assertEquals(1, queue.failedJobs().size());

        queue.replay(queue.failedJobs().get(0).id());
        assertTrue(queue.failedJobs().isEmpty());
        assertEquals(1, queue.pendingCount());
    }
}

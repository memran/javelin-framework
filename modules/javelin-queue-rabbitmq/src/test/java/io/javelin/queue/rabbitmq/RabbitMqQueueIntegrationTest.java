package io.javelin.queue.rabbitmq;

import io.javelin.core.queue.Job;
import io.javelin.queue.JobQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Broker-backed checks. Set {@code JAVELIN_RABBITMQ_URI} to run them, for example:
 * {@code docker run --rm -p 5672:5672 rabbitmq:3-alpine}.
 */
@EnabledIfEnvironmentVariable(named = "JAVELIN_RABBITMQ_URI", matches = ".+")
final class RabbitMqQueueIntegrationTest {
    @Test
    void dispatchesAcknowledgesDelaysAndTracksFailures() throws Exception {
        try (RabbitMqQueue queue = new RabbitMqQueue(System.getenv("JAVELIN_RABBITMQ_URI"))) {
            String tag = UUID.randomUUID().toString().substring(0, 8);
            Job direct = Job.of("test.direct." + tag, Map.of("tag", tag));
            queue.dispatch(direct);
            JobQueue.ClaimedJob claimed = claimById(queue, direct.id());
            queue.ack(direct.id(), claimed.leaseToken());
            assertEquals(tag, claimed.job().payload(Map.class).get("tag"));

            Job delayed = Job.of("test.delay." + tag, Map.of("tag", tag));
            queue.later(delayed, Duration.ofMillis(150));
            JobQueue.ClaimedJob delayedClaim = claimById(queue, delayed.id());
            queue.ack(delayed.id(), delayedClaim.leaseToken());

            Job failing = Job.of("test.fail." + tag, Map.of("tag", tag));
            queue.dispatch(failing);
            JobQueue.ClaimedJob failingClaim = claimById(queue, failing.id());
            queue.fail(failing.id(), failingClaim.leaseToken(), "boom", Duration.ZERO);
            assertTrue(queue.failedJobs().stream().anyMatch(job -> job.id().equals(failing.id())));

            queue.replay(failing.id());
            JobQueue.ClaimedJob replayed = claimBySubject(queue, "replay");
            queue.ack(replayed.id(), replayed.leaseToken());
            assertTrue(queue.pendingCount() >= 0);
        }
    }

    private static JobQueue.ClaimedJob claimById(RabbitMqQueue queue, String id) throws InterruptedException {
        long deadline = System.nanoTime() + Duration.ofSeconds(10).toNanos();
        while (System.nanoTime() < deadline) {
            Optional<JobQueue.ClaimedJob> claimed = queue.claim("integration", Duration.ofSeconds(5));
            if (claimed.isPresent()) {
                if (claimed.get().job().id().equals(id)) {
                    return claimed.get();
                }
                queue.ack(claimed.get().id(), claimed.get().leaseToken());
            }
            Thread.sleep(25);
        }
        throw new AssertionError("Job " + id + " was not delivered");
    }

    private static JobQueue.ClaimedJob claimBySubject(RabbitMqQueue queue, String subject) throws InterruptedException {
        long deadline = System.nanoTime() + Duration.ofSeconds(10).toNanos();
        while (System.nanoTime() < deadline) {
            Optional<JobQueue.ClaimedJob> claimed = queue.claim("integration", Duration.ofSeconds(5));
            if (claimed.isPresent()) {
                if (claimed.get().job().subject().equals(subject)) {
                    return claimed.get();
                }
                queue.ack(claimed.get().id(), claimed.get().leaseToken());
            }
            Thread.sleep(25);
        }
        throw new AssertionError("Job with subject " + subject + " was not delivered");
    }
}

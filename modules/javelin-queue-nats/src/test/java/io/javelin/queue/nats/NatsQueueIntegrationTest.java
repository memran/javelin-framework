package io.javelin.queue.nats;

import io.javelin.core.queue.Job;
import io.javelin.core.queue.RetryPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Broker-backed checks. Set {@code JAVELIN_NATS_URL} to run them, for example:
 * {@code docker run --rm -p 4222:4222 nats:2-alpine -js}.
 */
@EnabledIfEnvironmentVariable(named = "JAVELIN_NATS_URL", matches = ".+")
final class NatsQueueIntegrationTest {
    @Test
    void deliversThroughPushSubscriptionsWithoutAPollingOrFailedStore() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String stream = "IT" + id;
        String prefix = "it." + id;
        try (NatsQueue queue = new NatsQueue(System.getenv("JAVELIN_NATS_URL"), stream, prefix)) {
            AtomicReference<Job> received = new AtomicReference<>();
            CountDownLatch delivered = new CountDownLatch(1);
            queue.subscribe("orders", job -> {
                received.set(job);
                delivered.countDown();
            }, new RetryPolicy(2, Duration.ofMillis(50)));

            Job job = Job.of("orders", Map.of("tag", "value"));
            queue.publish(job);
            assertTrue(delivered.await(10, TimeUnit.SECONDS), "push subscription did not receive the job");
            assertEquals(job.id(), received.get().id());

            AtomicReference<Job> delayed = new AtomicReference<>();
            CountDownLatch delayedDelivery = new CountDownLatch(1);
            queue.subscribe("delayed", jobFromQueue -> {
                delayed.set(jobFromQueue);
                delayedDelivery.countDown();
            }, new RetryPolicy(2, Duration.ofMillis(50)));

            Job later = Job.of("delayed", Map.of("tag", "now"));
            queue.later(later, Duration.ofSeconds(30));
            assertTrue(delayedDelivery.await(2, TimeUnit.SECONDS), "later() should publish immediately on NATS");
            assertEquals(later.id(), delayed.get().id());

            assertTrue(queue.failedJobs().isEmpty());
            assertTrue(queue.claim("integration", Duration.ofSeconds(1)).isEmpty());
            assertEquals(0, queue.pendingCount());
        }
    }
}

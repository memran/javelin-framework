package io.javelin.queue.nats;

import io.javelin.core.Json;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class JobTest {
    @Test
    void jobPayloadSurvivesJsonRoundTrip() {
        Job job = Job.of("emails.welcome", Map.of("to", "ada@example.com", "retries", 2));

        Job restored = Job.parse(Json.stringify(job));

        assertEquals(job.id(), restored.id());
        assertEquals("emails.welcome", restored.subject());
        assertEquals("ada@example.com", restored.payload(Map.class).get("to"));
    }

    @Test
    void attemptsCanBeAdvancedForRetries() {
        Job job = Job.of("jobs.run", Map.of("id", 1));

        assertEquals(0, job.attempts());
        assertEquals(2, job.withAttempts(2).attempts());
        assertNotEquals(job.id(), Job.of("jobs.run", Map.of("id", 1)).id());
    }

    @Test
    void durableNamesAvoidCharactersNatsForbids() {
        assertEquals("demo-message", NatsQueue.durableName("demo.message"));
        assertEquals("a-b-c", NatsQueue.durableName("a/b>c"));
        assertEquals("worker", NatsQueue.durableName("..."));
    }

    @Test
    void retryPolicyRejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new RetryPolicy(0, Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class, () -> new RetryPolicy(2, Duration.ofSeconds(-1)));
        assertEquals(3, RetryPolicy.DEFAULT.maxAttempts());
    }
}

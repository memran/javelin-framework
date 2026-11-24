package io.javelin.core.queue;

import io.javelin.core.Json;

import java.util.Objects;
import java.util.UUID;

/** A queued unit of work. {@code payload} is JSON so it survives transport and retries. */
public record Job(String id, String subject, String payload, int attempts) {
    public Job {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(subject, "subject");
        Objects.requireNonNull(payload, "payload");
        if (subject.isBlank()) {
            throw new IllegalArgumentException("subject must not be blank");
        }
        if (attempts < 0) {
            throw new IllegalArgumentException("attempts must not be negative");
        }
    }

    public static Job of(String subject, Object payload) {
        return new Job(UUID.randomUUID().toString(), subject, Json.stringify(payload), 0);
    }

    public static Job parse(String json) {
        return Json.parse(json, Job.class);
    }

    public Job withAttempts(int value) {
        return new Job(id, subject, payload, value);
    }

    public <T> T payload(Class<T> type) {
        return Json.parse(payload, type);
    }
}

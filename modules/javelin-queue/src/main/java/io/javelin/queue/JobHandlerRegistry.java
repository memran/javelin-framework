package io.javelin.queue;

import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Explicit subject-to-handler registration. */
public final class JobHandlerRegistry {
    private final Map<String, Registration> registrations = new LinkedHashMap<>();

    public void register(String subject, JobHandler handler, RetryPolicy retryPolicy) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Job subject must not be blank");
        }
        registrations.put(subject, new Registration(
                Objects.requireNonNull(handler, "handler"),
                Objects.requireNonNull(retryPolicy, "retryPolicy")));
    }

    public Optional<Registration> find(String subject) {
        return Optional.ofNullable(registrations.get(subject));
    }

    public Set<String> subjects() {
        return Set.copyOf(registrations.keySet());
    }

    public record Registration(JobHandler handler, RetryPolicy retryPolicy) {
    }
}

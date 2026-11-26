package io.javelin.queues;

import io.javelin.core.queue.Job;
import io.javelin.queue.JobQueue;

import java.time.Duration;
import java.util.Objects;

/** Static facade for the active {@link JobQueue}. */
public final class Queue {
    private static volatile JobQueue active;

    private Queue() {
    }

    public static void use(JobQueue queue) {
        active = Objects.requireNonNull(queue, "queue");
    }

    public static JobQueue active() {
        return active;
    }

    public static void dispatch(Job job) {
        active().dispatch(job);
    }

    /** Dispatches any object as a job; the subject is derived from its class name. */
    public static void dispatch(Object job) {
        dispatch(toJob(job));
    }

    public static void later(Job job, Duration delay) {
        active().later(job, delay);
    }

    public static void later(Object job, Duration delay) {
        later(toJob(job), delay);
    }

    private static Job toJob(Object job) {
        Objects.requireNonNull(job, "job");
        if (job instanceof Job typed) {
            return typed;
        }
        String simple = job.getClass().getSimpleName();
        String base = simple.endsWith("Job") ? simple.substring(0, simple.length() - 3) : simple;
        String subject = base.replaceAll("([a-z])([A-Z])", "$1.$2")
                .toLowerCase(java.util.Locale.ROOT);
        return Job.of(subject, job);
    }
}

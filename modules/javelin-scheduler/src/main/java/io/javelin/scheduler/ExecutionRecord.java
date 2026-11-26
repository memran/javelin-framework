package io.javelin.scheduler;

import java.time.Instant;

/** One recorded execution of a scheduled task. */
public record ExecutionRecord(String task, Instant startedAt, Instant finishedAt, boolean success, String error) {
}

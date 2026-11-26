package io.javelin.scheduler;

/** A registered scheduled task. */
public record TaskDefinition(String name, ScheduleSpec schedule, ScheduledTask task, boolean allowOverlap) {
}

package io.javelin.scheduler;

import java.time.Instant;
import java.time.ZoneId;

/** Determines when a task runs next. */
public interface ScheduleSpec {
    Instant nextRun(Instant after, ZoneId zone);

    String description();
}

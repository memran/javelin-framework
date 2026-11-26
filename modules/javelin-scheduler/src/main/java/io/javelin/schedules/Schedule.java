package io.javelin.schedules;

import io.javelin.core.queue.Job;
import io.javelin.queues.Queue;
import io.javelin.scheduler.CronExpression;
import io.javelin.scheduler.ExecutionRecord;
import io.javelin.scheduler.ScheduleSpec;
import io.javelin.scheduler.ScheduledTask;
import io.javelin.scheduler.Scheduler;
import io.javelin.scheduler.Schedules;
import io.javelin.scheduler.TaskDefinition;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/** Static facade for registering scheduled tasks. */
public final class Schedule {
    private static volatile Scheduler active;

    private Schedule() {
    }

    public static void use(Scheduler scheduler) {
        active = Objects.requireNonNull(scheduler, "scheduler");
    }

    public static Scheduler active() {
        return active;
    }

    public static void dailyAt(String time, Class<? extends ScheduledTask> type) {
        dailyAt(time, instantiate(type));
    }

    public static void dailyAt(String time, ScheduledTask task) {
        register(nameOf(task), new Schedules.Daily(LocalTime.parse(time)), task);
    }

    public static void every(Duration interval, Class<? extends ScheduledTask> type) {
        every(interval, instantiate(type));
    }

    public static void every(Duration interval, ScheduledTask task) {
        register(nameOf(task), new Schedules.Interval(interval), task);
    }

    public static void cron(String expression, Class<? extends ScheduledTask> type) {
        cron(expression, instantiate(type));
    }

    public static void cron(String expression, ScheduledTask task) {
        register(nameOf(task), new Schedules.Cron(new CronExpression(expression)), task);
    }

    public static void weekly(DayOfWeek day, String time, Class<? extends ScheduledTask> type) {
        weekly(day, time, instantiate(type));
    }

    public static void weekly(DayOfWeek day, String time, ScheduledTask task) {
        register(nameOf(task), new Schedules.Weekly(day, LocalTime.parse(time)), task);
    }

    public static void monthly(int dayOfMonth, String time, Class<? extends ScheduledTask> type) {
        monthly(dayOfMonth, time, instantiate(type));
    }

    public static void monthly(int dayOfMonth, String time, ScheduledTask task) {
        register(nameOf(task), new Schedules.Monthly(dayOfMonth, LocalTime.parse(time)), task);
    }

    /** Schedules a job to be dispatched to the active queue on each run. */
    public static void dispatchJob(String name, ScheduleSpec spec, Supplier<Job> job) {
        register(name, spec, () -> Queue.dispatch(job.get()));
    }

    public static void register(String name, ScheduleSpec spec, ScheduledTask task) {
        active().register(new TaskDefinition(name, spec, task, false));
    }

    public static List<ExecutionRecord> history(int limit) {
        return active().history(limit);
    }

    private static String nameOf(Object task) {
        String simple = task.getClass().getSimpleName();
        return simple.isBlank() ? "task" : simple;
    }

    private static ScheduledTask instantiate(Class<? extends ScheduledTask> type) {
        Objects.requireNonNull(type, "type");
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new IllegalArgumentException("Scheduled task must have a no-argument constructor: " + type.getName(), exception);
        }
    }
}

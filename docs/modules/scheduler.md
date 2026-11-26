# Scheduler Module

Artifact: `io.javelin:javelin-scheduler`

`javelin-scheduler` runs tasks with a JDK `ScheduledExecutorService` (a daemon ticker plus virtual-thread workers), records every execution and can lock tasks across application instances.

## Developer API

```java
import io.javelin.schedules.Schedule;
import java.time.Duration;

Schedule.dailyAt("02:00", BackupJob.class);
Schedule.every(Duration.ofMinutes(5), HealthCheckJob.class);
Schedule.cron("0 2 * * *", BackupJob.class);
Schedule.weekly(DayOfWeek.MONDAY, "09:00", ReportJob.class);
Schedule.monthly(1, "03:00", InvoiceJob.class);
```

`BackupJob` implements `ScheduledTask` and has a no-argument constructor:

```java
public final class BackupJob implements ScheduledTask {
    @Override public void run() throws Exception {
        backupService.createSnapshot();
    }
}
```

Each registration uses the class name as the task name and disables overlap by default. Use `Schedule.register(name, spec, task)` for explicit control.

## Dispatching to queues

```java
Schedule.dispatchJob("send-invoices", new Schedules.Daily(LocalTime.of(2, 0)),
        () -> Job.of("invoices.due", Map.of("date", LocalDate.now().toString())));
```

Each run publishes a job through `io.javelin.queues.Queue`, so the actual work is handled by queue workers.

## Schedules

- `Schedules.Interval(Duration)` — fixed interval.
- `Schedules.Daily(LocalTime)` — daily at a time.
- `Schedules.Weekly(DayOfWeek, LocalTime)` — weekly.
- `Schedules.Monthly(int dayOfMonth, LocalTime)` — monthly, clamped to short months.
- `Schedules.Cron(CronExpression)` — five-field cron (`minute hour day month weekday`) supporting `*`, lists, ranges and steps. The parser is dependency-free; day-of-month and day-of-week use OR semantics when both are restricted.

## Configuration

```yaml
scheduler:
  enabled: true
  poll_interval_ms: 1000
  lock_ttl_seconds: 120
  timezone: UTC
  owner: ${HOSTNAME:javelin}
  history: memory    # or database
  lock: memory       # or database
```

`history: database` persists to `javelin_schedule_history`; `lock: database` uses `javelin_schedule_locks` so only one instance runs a given task at a time. Both use the existing `Database` binding.

## Reliability

- Overlap prevention skips a task that is still running.
- A task lock is acquired before execution and released in a `finally` block; stale locks expire after the TTL.
- Every run records start/finish time, success and error in `ExecutionHistory`.
- `Application.shutdown()` stops the ticker and waits for in-flight tasks.

`javelin schedule:run` runs all currently due tasks once, useful for cron-driven deployments where the app process is not always running.

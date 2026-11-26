# Scheduling and events

This tutorial uses the runnable `demo-app`. Both providers are registered by default and need no external service.

## 1. Configure

```yaml
scheduler:
  enabled: true
  poll_interval_ms: 1000
  lock_ttl_seconds: 120
  timezone: UTC
  history: memory    # use "database" for durable history
  lock: memory       # use "database" for multi-instance locking
```

`SchedulerServiceProvider` starts the ticker at boot and stops it during shutdown. `EventServiceProvider` activates the event facade and registers the queued-event handler with the queue worker.

## 2. Schedule tasks

```java
Schedule.dailyAt("02:00", BackupJob.class);
Schedule.every(Duration.ofMinutes(5), HealthCheckJob.class);
Schedule.cron("*/15 * * * *", PingJob.class);
Schedule.weekly(DayOfWeek.MONDAY, "09:00", ReportJob.class);
Schedule.monthly(1, "03:00", InvoiceJob.class);
```

A scheduled class implements `ScheduledTask`. To dispatch to the queue instead of running inline:

```java
Schedule.dispatchJob("nightly-emails", new Schedules.Daily(LocalTime.of(2, 0)),
        () -> Job.of("emails.daily", Map.of()));
```

Try the demo endpoints:

```bash
curl 'http://127.0.0.1:8080/demo/automation/schedule'
# {"started":1,"history":1}
```

The demo task writes `storage/automation/scheduled.txt`.

## 3. Dispatch events

```java
Events.listen(UserRegistered.class, event -> emailService.welcome(event.userId()));
Events.listenQueued(UserRegistered.class, event -> analyticsService.track(event.userId()));
Events.dispatch(new UserRegistered(userId));
```

The first listener runs synchronously; the second goes through the queue and shares its retries and failure handling:

```bash
curl 'http://127.0.0.1:8080/demo/automation/events?user=alice'
# {"dispatched":"alice"}
```

The demo writes `storage/automation/user-registered.txt`.

## 4. Multi-instance deployments

Set `scheduler.history: database` and `scheduler.lock: database` (or rely on the configured `Database` binding in Docker). The lock table ensures only one application instance executes a given task when it is due, and stale locks are released after `lock_ttl_seconds`.

## 5. Manual runs

```bash
javelin schedule:run
```

runs all due tasks once — handy when an external cron triggers the command instead of keeping the in-process scheduler running.

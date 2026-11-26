# Reliable background jobs

This tutorial uses the runnable `demo-app`, whose default queue driver is `file` — no Redis or RabbitMQ needed.

## 1. Configure the queue

Generated apps register `QueueServiceProvider` plus all adapter providers:

```yaml
queue:
  driver: file
  worker:
    enabled: true
    concurrency: 4
    poll_interval_ms: 500
    lease_seconds: 60
    timeout_seconds: 300
  retry:
    backoff: exponential
    initial_ms: 1000
    max_ms: 60000
  file:
    dir: storage/queues/file
```

Only the provider matching `queue.driver` binds a `JobQueue`; the others do nothing.

## 2. Define and dispatch a job

```java
public record SendEmailJob(String userId) {
}

Queue.dispatch(new SendEmailJob("42"));
Queue.later(new InvoiceJob("INV-1"), Duration.ofMinutes(5));
```

The facade derives the subject from the class name (`SendEmailJob` → `send.email`) and JSON-serializes the object as the payload. Try the demo:

```bash
curl 'http://127.0.0.1:8080/demo/queue/dispatch?user=42'
curl 'http://127.0.0.1:8080/demo/queue/later?id=INV-1'
curl 'http://127.0.0.1:8080/demo/queue/status'
```

## 3. Register a handler

```java
app.make(JobHandlerRegistry.class).register("send.email",
        job -> emailService.send(job.payload(SendEmailJob.class)),
        new RetryPolicy(3, Duration.ofSeconds(1)));
```

The demo writes a marker file under `storage/queue/` for each handled job.

## 4. Worker behavior

The background worker polls with bounded concurrency using virtual threads. Each job has a timeout; a failed job is rescheduled with exponential backoff until `maxAttempts`, then moved to failed-job storage. Leases prevent two workers from claiming the same job, and stale leases are recovered after a crash. Shutdown waits for in-flight jobs.

You can also run a foreground worker:

```bash
javelin queue:work --once     # process one job
javelin queue:work            # run until interrupted
javelin queue:status
```

## 5. Switch backend without changing code

```yaml
queue:
  driver: database          # or valkey, redis, rabbitmq, nats
  database:
    lease_seconds: 60
```

`Queue.dispatch`, `Queue.later`, handlers, retries and failed-job replay stay the same. See [Queue Adapters](../modules/queue-adapters.md) for backend-specific notes.

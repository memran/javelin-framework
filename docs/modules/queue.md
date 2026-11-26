# Queue Module

Artifact: `io.javelin:javelin-queue`

`javelin-queue` builds on the core queue contracts (`Job`, `JobHandler`, `RetryPolicy`) and adds the `JobQueue` contract, handler registry, worker and static facade.

## Developer API

```java
import io.javelin.queues.Queue;
import java.time.Duration;

Queue.dispatch(new SendEmailJob(userId));
Queue.later(new InvoiceJob(id), Duration.ofMinutes(5));
```

Any object can be dispatched: its class name becomes the subject (`SendEmailJob` → `send.email`) and the object is JSON-serialized as the payload. You can also dispatch a `Job` directly with an explicit subject.

## Contracts

- `JobQueue extends io.javelin.core.queue.Queue` — adds `dispatch`, `later`, `claim`, `ack`, `fail`, `failedJobs`, `replay` and `pendingCount`.
- `ClaimedJob` — job id, `Job` and a lease token that authorizes `ack`/`fail`.
- `JobHandlerRegistry` — explicit subject → (`JobHandler`, `RetryPolicy`) registration.

```java
app.make(JobHandlerRegistry.class).register("send.email",
        job -> emailService.send(job.payload(SendEmailJob.class)),
        new RetryPolicy(3, Duration.ofSeconds(1)));
```

## Worker

`QueueWorker` polls a `JobQueue` with bounded concurrency (virtual threads), applies a timeout per job, and acknowledges or reschedules failures. `QueueServiceProvider` starts it in the background when `queue.worker.enabled` is true (default) and closes it during `Application.shutdown()`.

`javelin queue:work --once` processes one job in the foreground; without `--once` it runs until interrupted. `javelin queue:status` shows the backend, pending count and handler count.

## Configuration

```yaml
queue:
  driver: file            # none | file | database | valkey | redis | rabbitmq | nats
  worker:
    enabled: true
    concurrency: 4
    poll_interval_ms: 500
    lease_seconds: 60
    timeout_seconds: 300
    shutdown_seconds: 10
  retry:
    backoff: exponential  # or constant
    initial_ms: 1000
    max_ms: 60000
```

Exponential backoff is `initial_ms * 2^(attempt-1)` capped at `max_ms`. The core `RetryPolicy.backoffFor(attempt)` remains constant for adapters that rely on it.

## Reliability

- Claims carry a lease; stale leases are recovered by the backend before the next claim.
- Unhandled handler failures are rescheduled with backoff until `maxAttempts`, then moved to failed-job storage.
- `failedJobs()` and `replay(id)` are available on every `JobQueue`.
- Graceful shutdown stops polling and waits for in-flight jobs up to `shutdown_seconds`.

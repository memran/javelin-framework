# Queue NATS Module

Artifact: `io.javelin:javelin-queue-nats`

A JetStream-backed `Queue` for publish/subscribe with explicit jobs and retry limits.

## Contracts (javelin-core)

```java
import io.javelin.core.queue.*;

Job job = Job.of("emails.welcome", Map.of("to", "ada@example.com"));
String id = job.id();
Map<?, ?> payload = job.payload(Map.class);

RetryPolicy retry = new RetryPolicy(5, Duration.ofSeconds(2));
```

- `Job` is an immutable record of `id`, `subject`, JSON `payload` and `attempts`; `Job.of(...)` creates one and `Job.parse(...)` restores one.
- `RetryPolicy` holds `maxAttempts` and `backoff`; `RetryPolicy.DEFAULT` is 3 attempts with a 1 second backoff.
- `Queue.publish(Job)` sends a job; `Queue.subscribe(subject, handler, retryPolicy)` registers a `JobHandler`.
- A handler that throws triggers a redelivery through JetStream's `nakWithDelay(backoff)` and `maxDeliver(maxAttempts)`.

## NatsQueue

```java
Queue queue = app.make(Queue.class);

queue.publish(Job.of("emails.welcome", Map.of("to", "ada@example.com")));

queue.subscribe("emails.welcome", job -> {
    Map<?, ?> data = job.payload(Map.class);
    // send email; throw to retry
}, RetryPolicy.DEFAULT);
```

- Subjects are namespaced: a job with subject `emails.welcome` is published to `javelin.emails.welcome` with the default prefix.
- On construction the queue creates the JetStream stream (file storage) if it does not exist, using `<prefix>.>` as its subject pattern.
- Each subscription creates or updates a durable consumer with explicit acknowledgements, `maxDeliver` and a backoff list.
- `attempts` passed to the handler comes from JetStream's delivery count.
- `close()` closes the NATS connection.

## NatsQueueServiceProvider

Activation is explicit: list the provider and select the driver.

```yaml
queue:
  driver: nats
  url: nats://nats:4222
  stream: JAVELIN
  subject_prefix: javelin

providers:
  - io.javelin.queue.nats.NatsQueueServiceProvider
```

| Config key | Default |
|---|---|
| `queue.driver` | `none` (must be `nats` to bind) |
| `queue.url` | `nats://localhost:4222` |
| `queue.stream` | `JAVELIN` |
| `queue.subject_prefix` | `javelin` |

The `Queue` singleton is resolved lazily, so listing the provider does not connect until the queue is first used.

## Notes

- The CLI `queue:work` / `queue:status` commands are implemented for the polling adapters (file, database, Valkey and RabbitMQ). `NatsQueue.claim()` and `pendingCount()` are no-ops because NATS uses push subscriptions, so register handlers with `Queue.subscribe(...)` for this driver.
- No server is started by the framework — use the generated `docker-compose.yml` (NATS with `-js`) or an external NATS server with JetStream enabled. The generated `queue.url` points at `nats://nats:4222` inside Compose; override `NATS_URL` for an external server.
- Unit tests cover job serialization, retry validation and provider selection without requiring a server.

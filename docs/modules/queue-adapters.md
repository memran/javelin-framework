# Queue Adapters

All adapters implement `JobQueue`, so `Queue.dispatch`, `Queue.later`, the worker and failed-job replay work the same way regardless of backend.

## File (`javelin-queue-file`)

```yaml
queue:
  driver: file
  file:
    dir: storage/queues/file
```

One JSON file per job in `pending/`, `processing/` and `failed/` directories. Claiming is an atomic file move; stale processing files are moved back after twice the lease. No external service required — this is the generated-app default.

## Valkey / Redis (`javelin-queue-valkey`)

```yaml
queue:
  driver: valkey        # "redis" is also accepted
  valkey:
    url: redis://localhost:6379
```

Lettuce-backed sorted sets hold pending jobs (score = run time) and processing jobs (score = claim time); failed jobs are kept in a list. Claims use `ZRANGEBYSCORE` followed by `ZREM` so only one worker wins.

## RabbitMQ (`javelin-queue-rabbitmq`)

```yaml
queue:
  driver: rabbitmq
  rabbitmq:
    uri: amqp://guest:guest@localhost:5672/
```

The main queue is `javelin.jobs`. Delayed jobs use a per-message TTL on `javelin.jobs.delay` with dead-lettering back to the main queue. Failed jobs are held in memory by the adapter instance; use the database adapter when failed-job durability across restarts matters.

## NATS JetStream (`javelin-queue-nats`)

```yaml
queue:
  driver: nats
  url: nats://localhost:4222
  stream: JAVELIN
```

The original push-consumer adapter. Handlers are registered through `Queue.subscribe(subject, handler, retryPolicy)` and retries are configured on the JetStream consumer. `later()` publishes immediately because JetStream push consumers have no native scheduled delivery; use the database or file adapter for durable delays. `fail()`, `failedJobs()`, `replay()` and `pendingCount()` are no-ops: unacknowledged messages are retained by JetStream, and there is no separate failed-job store.

## Choosing an adapter

| Driver | External service | Durable failed jobs | Delayed dispatch |
|---|---|---|---|
| `file` | none | files | yes |
| `database` | existing database | table | yes |
| `valkey` / `redis` | Valkey/Redis | list | yes (sorted set) |
| `rabbitmq` | RabbitMQ | in-memory | TTL queue |
| `nats` | NATS JetStream | JetStream redelivery (no failed-job store or replay API) | immediate |

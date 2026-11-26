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
    uri: amqp://guest:guest@localhost:5672/%2F
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

## Broker-backed integration tests

The RabbitMQ and NATS modules include opt-in integration tests. They are skipped in a normal `mvn verify` and run only when a broker URL is provided:

```bash
docker run --rm -d -p 5672:5672 rabbitmq:3-alpine
docker run --rm -d -p 4222:4222 nats:2-alpine -js

JAVELIN_RABBITMQ_URI=amqp://guest:guest@localhost:5672/%2F \
  mvn -pl :javelin-queue-rabbitmq test
JAVELIN_NATS_URL=nats://localhost:4222 \
  mvn -pl :javelin-queue-nats test
```

The RabbitMQ test covers dispatch/claim/ack, delayed delivery, failure recording and replay. The NATS test covers push delivery, immediate `later()` publishing and the absence of polling/failed-job APIs.

## Choosing an adapter

| Driver | External service | Durable failed jobs | Delayed dispatch |
|---|---|---|---|
| `file` | none | files | yes |
| `database` | existing database | table | yes |
| `valkey` / `redis` | Valkey/Redis | list | yes (sorted set) |
| `rabbitmq` | RabbitMQ | in-memory | TTL queue |
| `nats` | NATS JetStream | JetStream redelivery (no failed-job store or replay API) | immediate |

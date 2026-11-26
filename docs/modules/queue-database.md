# Database Queue Module

Artifact: `io.javelin:javelin-queue-database`

A durable `JobQueue` backed by the existing `Database` contract — no Redis or RabbitMQ required.

## Configuration

```yaml
queue:
  driver: database
  database:
    lease_seconds: 60
```

`DatabaseQueueServiceProvider` creates the queue only when `queue.driver` is `database`. Tables are created on first use:

- `javelin_jobs` — id, subject, JSON payload, attempts, max attempts, `run_at`, `locked_at`, `locked_by`, last error and creation time.
- `javelin_failed_jobs` — id, subject, payload, attempts, error and failure time.

All columns use portable types (`varchar`, `text`, `integer`, `bigint`) with integer epoch times, so the same schema works on PostgreSQL, MySQL/MariaDB and SQLite.

## Behavior

- `dispatch` inserts a pending job; `later` inserts one with a future `run_at`.
- `claim` releases leases older than the configured lease, then uses a compare-and-swap update (`where ... and locked_at = 0`) so two workers cannot claim the same job.
- `ack` deletes the job. `fail` reschedules with backoff or moves it to `javelin_failed_jobs` once `maxAttempts` is reached.
- `replay(id)` returns a failed job to the pending table with attempts reset to zero.
- `pendingCount()` counts jobs whose `run_at` is due and that are not locked.

The queue uses the application's configured HikariCP pool and connection settings. Register this provider after your database provider so the `Database` binding exists.

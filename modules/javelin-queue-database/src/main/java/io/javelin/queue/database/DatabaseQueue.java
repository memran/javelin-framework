package io.javelin.queue.database;

import io.javelin.core.Database;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobQueue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Broker-free durable queue using the core Database contract. */
public final class DatabaseQueue implements JobQueue {
    public static final String JOBS_TABLE = "javelin_jobs";
    public static final String FAILED_TABLE = "javelin_failed_jobs";

    private final Database database;
    private final Duration lease;

    public DatabaseQueue(Database database) {
        this(database, Duration.ofSeconds(60));
    }

    public DatabaseQueue(Database database, Duration lease) {
        this.database = Objects.requireNonNull(database, "database");
        this.lease = Objects.requireNonNull(lease, "lease");
        ensureTables();
    }

    private void ensureTables() {
        database.execute("""
                create table if not exists javelin_jobs (
                    id varchar(64) primary key,
                    subject varchar(255) not null,
                    payload text not null,
                    attempts integer not null,
                    max_attempts integer not null,
                    run_at bigint not null,
                    locked_at bigint not null default 0,
                    locked_by varchar(255) not null default '',
                    last_error varchar(2000) not null default '',
                    created_at bigint not null
                )
                """);
        database.execute("""
                create table if not exists javelin_failed_jobs (
                    id varchar(64) primary key,
                    subject varchar(255) not null,
                    payload text not null,
                    attempts integer not null,
                    error varchar(2000) not null,
                    failed_at bigint not null
                )
                """);
    }

    @Override
    public void dispatch(Job job) {
        insertJob(job, RetryPolicy.DEFAULT.maxAttempts(), System.currentTimeMillis());
    }

    @Override
    public void later(Job job, Duration delay) {
        insertJob(job, RetryPolicy.DEFAULT.maxAttempts(), System.currentTimeMillis() + delay.toMillis());
    }

    private void insertJob(Job job, int maxAttempts, long runAt) {
        Objects.requireNonNull(job, "job");
        long now = System.currentTimeMillis();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", job.id());
        row.put("subject", job.subject());
        row.put("payload", job.payload());
        row.put("attempts", job.attempts());
        row.put("max_attempts", maxAttempts);
        row.put("run_at", runAt);
        row.put("created_at", now);
        database.table(JOBS_TABLE).insert(row);
    }

    @Override
    public Optional<ClaimedJob> claim(String workerId, Duration claimLease) {
        long now = System.currentTimeMillis();
        database.execute("update " + JOBS_TABLE
                + " set locked_at = 0, locked_by = '' where locked_at <> 0 and locked_at < "
                + (now - lease.toMillis()));
        for (Map<String, Object> row : database.table(JOBS_TABLE).get()) {
            if (longValue(row.get("locked_at")) != 0 || longValue(row.get("run_at")) > now) {
                continue;
            }
            String id = string(row.get("id"));
            String token = workerId + ":" + UUID.randomUUID();
            int updated = database.execute("update " + JOBS_TABLE
                    + " set locked_at = " + now + ", locked_by = '" + escaped(token)
                    + "' where id = '" + escaped(id) + "' and locked_at = 0");
            if (updated != 1) {
                continue;
            }
            Optional<Map<String, Object>> claimed = database.table(JOBS_TABLE).where("id", id).first();
            if (claimed.isPresent()) {
                return Optional.of(new ClaimedJob(id, toJob(claimed.get()), token));
            }
        }
        return Optional.empty();
    }

    @Override
    public void ack(String id, String leaseToken) {
        database.execute("delete from " + JOBS_TABLE
                + " where id = '" + escaped(id) + "' and locked_by = '" + escaped(leaseToken) + "'");
    }

    @Override
    public void fail(String id, String leaseToken, String error, Duration retryDelay) {
        Optional<Map<String, Object>> found = database.table(JOBS_TABLE).where("id", id).first();
        if (found.isEmpty() || !string(found.get().get("locked_by")).equals(leaseToken)) {
            return;
        }
        Map<String, Object> row = found.get();
        int attempts = intValue(row.get("attempts")) + 1;
        int maxAttempts = intValue(row.get("max_attempts"));
        if (attempts >= maxAttempts) {
            Map<String, Object> failed = new LinkedHashMap<>();
            failed.put("id", id);
            failed.put("subject", string(row.get("subject")));
            failed.put("payload", string(row.get("payload")));
            failed.put("attempts", attempts);
            failed.put("error", trim(error));
            failed.put("failed_at", System.currentTimeMillis());
            database.table(FAILED_TABLE).insert(failed);
            database.execute("delete from " + JOBS_TABLE + " where id = '" + escaped(id) + "'");
            return;
        }
        database.execute("update " + JOBS_TABLE
                + " set attempts = " + attempts
                + ", run_at = " + (System.currentTimeMillis() + retryDelay.toMillis())
                + ", locked_at = 0, locked_by = '', last_error = '" + escaped(trim(error))
                + "' where id = '" + escaped(id) + "'");
    }

    @Override
    public List<Job> failedJobs() {
        List<Job> jobs = new ArrayList<>();
        for (Map<String, Object> row : database.table(FAILED_TABLE).get()) {
            jobs.add(toJob(row));
        }
        return List.copyOf(jobs);
    }

    @Override
    public void replay(String failedId) {
        Optional<Map<String, Object>> found = database.table(FAILED_TABLE).where("id", failedId).first();
        if (found.isEmpty()) {
            return;
        }
        Map<String, Object> row = found.get();
        Map<String, Object> restored = new LinkedHashMap<>();
        restored.put("id", string(row.get("id")));
        restored.put("subject", string(row.get("subject")));
        restored.put("payload", string(row.get("payload")));
        restored.put("attempts", 0);
        restored.put("max_attempts", RetryPolicy.DEFAULT.maxAttempts());
        restored.put("run_at", System.currentTimeMillis());
        restored.put("created_at", System.currentTimeMillis());
        database.table(JOBS_TABLE).insert(restored);
        database.execute("delete from " + FAILED_TABLE + " where id = '" + escaped(failedId) + "'");
    }

    @Override
    public long pendingCount() {
        long now = System.currentTimeMillis();
        return database.table(JOBS_TABLE).get().stream()
                .filter(row -> longValue(row.get("locked_at")) == 0 && longValue(row.get("run_at")) <= now)
                .count();
    }

    @Override
    public void publish(Job job) {
        dispatch(job);
    }

    @Override
    public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) {
        // Workers consume through JobHandlerRegistry; this adapter is pull-based.
    }

    @Override
    public void close() {
        // The Database connection pool is owned by the container.
    }

    private static Job toJob(Map<String, Object> row) {
        return new Job(string(row.get("id")), string(row.get("subject")), string(row.get("payload")),
                intValue(row.get("attempts")));
    }

    private static String escaped(String value) {
        return value.replace("'", "''");
    }

    private static String trim(String value) {
        if (value == null) {
            return "";
        }
        return value.length() > 1900 ? value.substring(0, 1900) : value;
    }

    private static String string(Object value) {
        return value == null ? "" : value.toString();
    }

    private static long longValue(Object value) {
        return value instanceof Number number ? number.longValue() : 0L;
    }

    private static int intValue(Object value) {
        return value instanceof Number number ? number.intValue() : 0;
    }
}

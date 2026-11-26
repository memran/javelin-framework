package io.javelin.queue.valkey;

import io.javelin.core.Json;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobQueue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Lettuce-backed queue using sorted sets for delayed jobs and leases. */
public final class ValkeyQueue implements JobQueue {
    private static final String PENDING = "javelin:queue:pending";
    private static final String PROCESSING = "javelin:queue:processing";
    private static final String FAILED = "javelin:queue:failed";

    private final RedisClient client;
    private final StatefulRedisConnection<String, String> connection;

    public ValkeyQueue(String url) {
        this.client = RedisClient.create(Objects.requireNonNull(url, "url"));
        this.connection = client.connect();
    }

    private RedisCommands<String, String> commands() {
        return connection.sync();
    }

    @Override
    public void dispatch(Job job) {
        commands().zadd(PENDING, System.currentTimeMillis(), Json.stringify(job));
    }

    @Override
    public void later(Job job, Duration delay) {
        commands().zadd(PENDING, System.currentTimeMillis() + delay.toMillis(), Json.stringify(job));
    }

    @Override
    public Optional<ClaimedJob> claim(String workerId, Duration lease) {
        RedisCommands<String, String> commands = commands();
        long now = System.currentTimeMillis();
        recoverStale(commands, now, lease);
        List<String> candidates = commands.zrangebyscore(PENDING, 0.0, (double) now, 0L, 1L);
        if (candidates.isEmpty()) {
            return Optional.empty();
        }
        String payload = candidates.get(0);
        if (commands.zrem(PENDING, payload) != 1L) {
            return Optional.empty();
        }
        String token = workerId + ":" + UUID.randomUUID();
        commands.zadd(PROCESSING, now, token + "|" + payload);
        Job job = Json.parse(payload, Job.class);
        return Optional.of(new ClaimedJob(job.id(), job, token));
    }

    private void recoverStale(RedisCommands<String, String> commands, long now, Duration lease) {
        long cutoff = now - lease.toMillis() * 2;
        for (String entry : commands.zrangebyscore(PROCESSING, 0.0, (double) cutoff)) {
            if (commands.zrem(PROCESSING, entry) == 1L) {
                String payload = entry.substring(entry.indexOf('|') + 1);
                commands.zadd(PENDING, now, payload);
            }
        }
    }

    @Override
    public void ack(String id, String leaseToken) {
        String entry = findProcessing(leaseToken).orElse(null);
        if (entry != null) {
            commands().zrem(PROCESSING, entry);
        }
    }

    @Override
    public void fail(String id, String leaseToken, String error, Duration retryDelay) {
        String entry = findProcessing(leaseToken).orElse(null);
        if (entry == null) {
            return;
        }
        commands().zrem(PROCESSING, entry);
        Job job = Json.parse(entry.substring(entry.indexOf('|') + 1), Job.class);
        int attempts = job.attempts() + 1;
        if (attempts >= RetryPolicy.DEFAULT.maxAttempts()) {
            commands().lpush(FAILED, Json.stringify(job.withAttempts(attempts)));
        } else {
            commands().zadd(PENDING, System.currentTimeMillis() + retryDelay.toMillis(),
                    Json.stringify(job.withAttempts(attempts)));
        }
    }

    private Optional<String> findProcessing(String leaseToken) {
        String prefix = leaseToken + "|";
        return commands().zrange(PROCESSING, 0, -1).stream()
                .filter(entry -> entry.startsWith(prefix))
                .findFirst();
    }

    @Override
    public List<Job> failedJobs() {
        List<Job> jobs = new ArrayList<>();
        for (String payload : commands().lrange(FAILED, 0, -1)) {
            jobs.add(Json.parse(payload, Job.class));
        }
        return List.copyOf(jobs);
    }

    @Override
    public void replay(String failedId) {
        for (String payload : commands().lrange(FAILED, 0, -1)) {
            Job job = Json.parse(payload, Job.class);
            if (job.id().equals(failedId)) {
                commands().lrem(FAILED, 1, payload);
                commands().zadd(PENDING, System.currentTimeMillis(), Json.stringify(job.withAttempts(0)));
                return;
            }
        }
    }

    @Override
    public long pendingCount() {
        return commands().zcount(PENDING, 0, System.currentTimeMillis());
    }

    @Override
    public void publish(Job job) {
        dispatch(job);
    }

    @Override
    public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) {
        // Workers consume through JobHandlerRegistry.
    }

    @Override
    public void close() {
        connection.close();
        client.shutdown();
    }
}

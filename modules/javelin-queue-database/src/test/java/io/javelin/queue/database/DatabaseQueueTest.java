package io.javelin.queue.database;

import com.zaxxer.hikari.HikariDataSource;
import io.javelin.core.queue.Job;
import io.javelin.db.jdbc.JdbcDatabase;
import io.javelin.db.jdbc.SqlTimer;
import io.javelin.queue.JobQueue.ClaimedJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DatabaseQueueTest {
    @Test
    void dispatchClaimAckAndDelayedJobs(@TempDir Path dir) throws Exception {
        try (JdbcDatabase database = database(dir)) {
            DatabaseQueue queue = new DatabaseQueue(database, Duration.ofSeconds(60));
            Job job = Job.of("emails.welcome", Map.of("to", "alice"));

            queue.dispatch(job);
            assertEquals(1, queue.pendingCount());

            ClaimedJob claimed = queue.claim("worker-1", Duration.ofSeconds(30)).orElseThrow();
            assertEquals(job.subject(), claimed.job().subject());
            assertEquals(0, queue.pendingCount());

            queue.ack(claimed.id(), claimed.leaseToken());
            assertEquals(0, queue.pendingCount());

            queue.later(Job.of("invoices.due", Map.of("id", 7)), Duration.ofMinutes(5));
            assertEquals(0, queue.pendingCount());
            assertTrue(queue.claim("worker-1", Duration.ofSeconds(30)).isEmpty());
        }
    }

    @Test
    void failedJobsAreStoredAndReplayable(@TempDir Path dir) throws Exception {
        try (JdbcDatabase database = database(dir)) {
            DatabaseQueue queue = new DatabaseQueue(database, Duration.ofSeconds(60));
            queue.dispatch(Job.of("failing.job", Map.of("id", 1)));

            for (int attempt = 1; attempt <= 3; attempt++) {
                ClaimedJob claimed = queue.claim("worker-1", Duration.ofSeconds(30)).orElseThrow();
                assertEquals(attempt - 1, claimed.job().attempts());
                queue.fail(claimed.id(), claimed.leaseToken(), "boom", Duration.ZERO);
            }

            assertEquals(1, queue.failedJobs().size());
            assertEquals(0, queue.pendingCount());

            String failedId = queue.failedJobs().get(0).id();
            queue.replay(failedId);
            assertTrue(queue.failedJobs().isEmpty());
            assertEquals(1, queue.pendingCount());
        }
    }

    @Test
    void staleLeasesAreRecovered(@TempDir Path dir) throws Exception {
        try (JdbcDatabase database = database(dir)) {
            DatabaseQueue queue = new DatabaseQueue(database, Duration.ofSeconds(60));
            queue.dispatch(Job.of("stale.job", Map.of()));
            ClaimedJob claimed = queue.claim("crashed-worker", Duration.ofSeconds(30)).orElseThrow();

            database.execute("update javelin_jobs set locked_at = 1 where id = '" + claimed.id() + "'");

            ClaimedJob recovered = queue.claim("worker-2", Duration.ofSeconds(30)).orElseThrow();
            assertEquals(claimed.id(), recovered.id());
        }
    }

    private static JdbcDatabase database(Path dir) {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:sqlite:" + dir.resolve("queue.db"));
        source.setMaximumPoolSize(2);
        return new JdbcDatabase(source, SqlTimer.disabled());
    }
}

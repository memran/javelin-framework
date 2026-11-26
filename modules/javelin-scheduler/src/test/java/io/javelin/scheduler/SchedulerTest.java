package io.javelin.scheduler;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SchedulerTest {
    @Test
    void cronComputesNextRun() {
        CronExpression cron = new CronExpression("0 2 * * *");
        Instant next = cron.nextRun(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
        assertEquals(Instant.parse("2026-01-01T02:00:00Z"), next);
    }

    @Test
    void intervalTasksRunAndRecordHistory() throws Exception {
        AtomicInteger runs = new AtomicInteger();
        Scheduler scheduler = scheduler(new InMemorySchedulerLock());
        scheduler.register(new TaskDefinition("tick", new Schedules.Interval(Duration.ZERO),
                runs::incrementAndGet, false));

        assertEquals(1, scheduler.runDue());
        Thread.sleep(150);

        assertEquals(1, runs.get());
        assertEquals(1, scheduler.history(10).size());
        assertTrue(scheduler.history(10).get(0).success());
        scheduler.close();
    }

    @Test
    void overlappingRunsArePrevented() {
        Scheduler scheduler = scheduler(new InMemorySchedulerLock());
        scheduler.register(new TaskDefinition("slow", new Schedules.Interval(Duration.ZERO),
                () -> Thread.sleep(200), false));

        assertEquals(1, scheduler.runDue());
        assertEquals(0, scheduler.runDue());
        scheduler.close();
    }

    @Test
    void lockFailureSkipsTask() {
        SchedulerLock locked = new SchedulerLock() {
            @Override public boolean tryLock(String task, String owner, Duration ttl) { return false; }
            @Override public void release(String task, String owner) { }
        };
        Scheduler scheduler = scheduler(locked);
        scheduler.register(new TaskDefinition("locked", new Schedules.Interval(Duration.ZERO),
                () -> { }, false));

        assertEquals(0, scheduler.runDue());
        scheduler.close();
    }

    private static Scheduler scheduler(SchedulerLock lock) {
        SchedulerConfig config = new SchedulerConfig(false, Duration.ofMillis(10),
                Duration.ofSeconds(5), ZoneOffset.UTC, "test", "memory");
        return new Scheduler(new InMemoryExecutionHistory(), lock, config);
    }
}

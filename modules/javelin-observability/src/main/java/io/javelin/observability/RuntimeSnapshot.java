package io.javelin.observability;

import java.util.LinkedHashMap;
import java.util.Map;

/** JVM/virtual-thread runtime metrics. */
public record RuntimeSnapshot(long uptimeMs, long heapUsedMb, long heapMaxMb, long nonHeapUsedMb,
                              int threads, int daemonThreads, int schedulerParallelism) {
    public static RuntimeSnapshot capture() {
        Runtime runtime = Runtime.getRuntime();
        return new RuntimeSnapshot(
                java.lang.management.ManagementFactory.getRuntimeMXBean().getUptime(),
                (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024),
                runtime.maxMemory() / (1024 * 1024),
                java.lang.management.ManagementFactory.getMemoryMXBean()
                        .getNonHeapMemoryUsage().getUsed() / (1024 * 1024),
                Thread.activeCount(),
                (int) Thread.getAllStackTraces().keySet().stream()
                        .filter(Thread::isDaemon).count(),
                Integer.getInteger("jdk.virtualThreadScheduler.parallelism",
                        runtime.availableProcessors()));
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("uptime_ms", uptimeMs);
        values.put("heap_used_mb", heapUsedMb);
        values.put("heap_max_mb", heapMaxMb);
        values.put("non_heap_used_mb", nonHeapUsedMb);
        values.put("threads", threads);
        values.put("daemon_threads", daemonThreads);
        values.put("virtual_thread_scheduler_parallelism", schedulerParallelism);
        return values;
    }
}

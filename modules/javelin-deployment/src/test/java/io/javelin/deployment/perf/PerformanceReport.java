package io.javelin.deployment.perf;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * Collects small, repeatable measurements and writes a dashboard-friendly JSON report.
 * Used only by the performance regression tests; no production code depends on it.
 */
public final class PerformanceReport {
    private static volatile Object sink;

    private final String suite;
    private final List<Measurement> measurements = new ArrayList<>();

    public record Measurement(String name, long operations, long durationMs, double nsPerOperation,
                              double operationsPerSecond, double minimumOperationsPerSecond) {
        public boolean passed() {
            return operationsPerSecond >= minimumOperationsPerSecond;
        }
    }

    public PerformanceReport(String suite) {
        this.suite = suite;
    }

    /**
     * Warms up, runs three timed rounds and records the median. The returned value is
     * consumed so the JIT cannot discard the measured work.
     */
    public Measurement measure(String name, long operations, double minimumOperationsPerSecond, Supplier<?> work) {
        if (operations < 1) {
            throw new IllegalArgumentException("Operations must be at least 1");
        }
        int warmup = (int) Math.min(2_000, Math.max(100, operations / 20));
        for (int index = 0; index < warmup; index++) {
            sink = work.get();
        }
        long[] rounds = new long[3];
        for (int round = 0; round < rounds.length; round++) {
            Object last = null;
            long started = System.nanoTime();
            for (long index = 0; index < operations; index++) {
                last = work.get();
            }
            rounds[round] = System.nanoTime() - started;
            sink = last;
        }
        Arrays.sort(rounds);
        long duration = Math.max(1, rounds[1]);
        Measurement measurement = new Measurement(name, operations, duration / 1_000_000,
                duration / (double) operations, operations * 1_000_000_000.0 / duration,
                minimumOperationsPerSecond);
        record(measurement);
        return measurement;
    }

    /** Records a measurement taken by an external harness, such as the concurrent HTTP load test. */
    public void record(Measurement measurement) {
        measurements.add(measurement);
        Assertions.assertTrue(measurement.passed(), () -> measurement.name()
                + " throughput " + String.format(Locale.ROOT, "%.0f", measurement.operationsPerSecond())
                + " ops/s is below floor "
                + String.format(Locale.ROOT, "%.0f", measurement.minimumOperationsPerSecond()) + " ops/s");
    }

    /** Prints one line per metric and writes {@code target/performance-<suite>.json}. */
    public void publish() {
        System.out.println("[PERF] suite=" + suite + " java=" + System.getProperty("java.version"));
        for (Measurement measurement : measurements) {
            System.out.printf(Locale.ROOT,
                    "[PERF] %-32s ops=%-8d ms=%-6d ns/op=%-8.0f ops/s=%-10.0f floor=%-8.0f %s%n",
                    measurement.name(), measurement.operations(), measurement.durationMs(),
                    measurement.nsPerOperation(), measurement.operationsPerSecond(),
                    measurement.minimumOperationsPerSecond(), measurement.passed() ? "PASS" : "FAIL");
        }
        try {
            Path report = Path.of("target").resolve("performance-" + suite + ".json");
            Files.createDirectories(report.getParent());
            Files.writeString(report, json(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException("Unable to write performance report", exception);
        }
    }

    private String json() {
        StringBuilder result = new StringBuilder();
        result.append("{\n  \"suite\": \"").append(escape(suite)).append("\",\n");
        result.append("  \"generated_at\": \"").append(Instant.now()).append("\",\n");
        result.append("  \"java_version\": \"").append(escape(System.getProperty("java.version"))).append("\",\n");
        result.append("  \"measurements\": [\n");
        for (int index = 0; index < measurements.size(); index++) {
            Measurement measurement = measurements.get(index);
            result.append("    {\"name\": \"").append(escape(measurement.name())).append("\",");
            result.append("\"operations\": ").append(measurement.operations()).append(',');
            result.append("\"duration_ms\": ").append(measurement.durationMs()).append(',');
            result.append("\"ns_per_operation\": ").append(round(measurement.nsPerOperation())).append(',');
            result.append("\"operations_per_second\": ").append(round(measurement.operationsPerSecond())).append(',');
            result.append("\"minimum_operations_per_second\": ")
                    .append(round(measurement.minimumOperationsPerSecond())).append(',');
            result.append("\"passed\": ").append(measurement.passed()).append('}');
            result.append(index + 1 < measurements.size() ? ",\n" : "\n");
        }
        result.append("  ]\n}\n");
        return result.toString();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

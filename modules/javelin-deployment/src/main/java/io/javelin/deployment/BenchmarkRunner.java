package io.javelin.deployment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/** Small repeatable load test using JDK HttpClient and virtual threads. */
public final class BenchmarkRunner {
    public record Result(String url, long total, long successes, long failures,
                         double requestsPerSecond, long p50Ms, long p95Ms, long durationMs) {
    }

    public Result run(String url, int concurrency, Duration duration) throws Exception {
        if (concurrency < 1) {
            throw new IllegalArgumentException("Concurrency must be at least 1");
        }
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        AtomicLong total = new AtomicLong();
        AtomicLong successes = new AtomicLong();
        AtomicLong failures = new AtomicLong();
        List<Long> latencies = Collections.synchronizedList(new ArrayList<>());
        long deadline = System.nanoTime() + duration.toNanos();
        long started = System.nanoTime();

        ExecutorService workers = Executors.newVirtualThreadPerTaskExecutor();
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int index = 0; index < concurrency; index++) {
                futures.add(workers.submit(() -> {
                    while (System.nanoTime() < deadline) {
                        long start = System.nanoTime();
                        try {
                            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                            if (response.statusCode() >= 200 && response.statusCode() < 400) {
                                successes.incrementAndGet();
                            } else {
                                failures.incrementAndGet();
                            }
                        } catch (Exception exception) {
                            failures.incrementAndGet();
                        } finally {
                            latencies.add((System.nanoTime() - start) / 1_000_000);
                            total.incrementAndGet();
                        }
                    }
                }));
            }
            for (Future<?> future : futures) {
                future.get();
            }
        } finally {
            workers.shutdownNow();
        }

        long elapsedMillis = Math.max(1, (System.nanoTime() - started) / 1_000_000);
        List<Long> sorted = new ArrayList<>(latencies);
        Collections.sort(sorted);
        return new Result(url, total.get(), successes.get(), failures.get(),
                total.get() * 1000.0 / elapsedMillis,
                percentile(sorted, 0.50), percentile(sorted, 0.95), elapsedMillis);
    }

    private static long percentile(List<Long> sorted, double fraction) {
        if (sorted.isEmpty()) {
            return 0;
        }
        int index = Math.min(sorted.size() - 1, (int) (sorted.size() * fraction));
        return sorted.get(index);
    }
}

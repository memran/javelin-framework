package io.javelin.queue.file;

import io.javelin.core.Json;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobQueue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/** Broker-free queue that stores one JSON file per job. */
public final class FileQueue implements JobQueue {
    private final Path root;
    private final Path pending;
    private final Path processing;
    private final Path failed;

    public FileQueue(Path root) {
        this.root = Objects.requireNonNull(root, "root").toAbsolutePath().normalize();
        try {
            this.pending = Files.createDirectories(root.resolve("pending"));
            this.processing = Files.createDirectories(root.resolve("processing"));
            this.failed = Files.createDirectories(root.resolve("failed"));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create queue directories under " + root, exception);
        }
    }

    @Override
    public void dispatch(Job job) {
        store(job, RetryPolicy.DEFAULT.maxAttempts(), System.currentTimeMillis());
    }

    @Override
    public void later(Job job, Duration delay) {
        store(job, RetryPolicy.DEFAULT.maxAttempts(), System.currentTimeMillis() + delay.toMillis());
    }

    private void store(Job job, int maxAttempts, long runAt) {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("job", Json.stringify(job));
        record.put("runAt", runAt);
        record.put("attempts", job.attempts());
        record.put("maxAttempts", maxAttempts);
        write(pending.resolve(job.id() + ".json"), record);
    }

    @Override
    public Optional<ClaimedJob> claim(String workerId, Duration lease) {
        long now = System.currentTimeMillis();
        recoverStale(now, lease);
        for (Path file : files(pending)) {
            Map<String, Object> record = read(file);
            if (record == null || number(record.get("runAt")).longValue() > now) {
                continue;
            }
            String token = UUID.randomUUID().toString();
            Path target = processing.resolve(file.getFileName().toString().replace(".json", "__" + token + ".json"));
            if (move(file, target)) {
                Job job = Json.parse(string(record.get("job")), Job.class);
                return Optional.of(new ClaimedJob(job.id(), job, token));
            }
        }
        return Optional.empty();
    }

    private void recoverStale(long now, Duration lease) {
        for (Path file : files(processing)) {
            long claimedAt = parseClaimedAt(file);
            if (now - claimedAt > lease.toMillis() * 2) {
                String id = file.getFileName().toString().split("__")[0];
                move(file, pending.resolve(id + ".json"));
            }
        }
    }

    private long parseClaimedAt(Path file) {
        try {
            return Files.getLastModifiedTime(file).toMillis();
        } catch (IOException exception) {
            return System.currentTimeMillis();
        }
    }

    @Override
    public void ack(String id, String leaseToken) {
        find(processing, id, leaseToken).ifPresent(path -> delete(path));
    }

    @Override
    public void fail(String id, String leaseToken, String error, Duration retryDelay) {
        Path claimed = find(processing, id, leaseToken).orElse(null);
        if (claimed == null) {
            return;
        }
        Map<String, Object> record = read(claimed);
        delete(claimed);
        if (record == null) {
            return;
        }
        int attempts = number(record.get("attempts")).intValue() + 1;
        int maxAttempts = number(record.get("maxAttempts")).intValue();
        record.put("attempts", attempts);
        record.put("lastError", error == null ? "" : error);
        record.put("runAt", System.currentTimeMillis() + retryDelay.toMillis());
        if (attempts >= maxAttempts) {
            write(failed.resolve(id + ".json"), record);
        } else {
            write(pending.resolve(id + ".json"), record);
        }
    }

    @Override
    public List<Job> failedJobs() {
        List<Job> jobs = new ArrayList<>();
        for (Path file : files(failed)) {
            Map<String, Object> record = read(file);
            if (record != null) {
                jobs.add(Json.parse(string(record.get("job")), Job.class));
            }
        }
        return List.copyOf(jobs);
    }

    @Override
    public void replay(String failedId) {
        Path file = failed.resolve(failedId + ".json");
        if (!Files.isRegularFile(file)) {
            return;
        }
        Map<String, Object> record = read(file);
        if (record == null) {
            return;
        }
        record.put("attempts", 0);
        record.put("runAt", System.currentTimeMillis());
        write(pending.resolve(failedId + ".json"), record);
        delete(file);
    }

    @Override
    public long pendingCount() {
        long now = System.currentTimeMillis();
        return files(pending).stream()
                .map(path -> read(path))
                .filter(record -> record != null && number(record.get("runAt")).longValue() <= now)
                .count();
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
        // File handles are opened per operation.
    }

    private Optional<Path> find(Path directory, String id, String token) {
        return files(directory).stream()
                .filter(path -> path.getFileName().toString().startsWith(id + "__" + token))
                .findFirst();
    }

    private static List<Path> files(Path directory) {
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to list queue directory " + directory, exception);
        }
    }

    private static Map<String, Object> read(Path file) {
        try {
            return Json.parse(Files.readString(file, StandardCharsets.UTF_8), Map.class);
        } catch (IOException exception) {
            return null;
        }
    }

    private static void write(Path file, Map<String, Object> record) {
        try {
            Files.writeString(file, Json.stringify(record), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write queue file " + file, exception);
        }
    }

    private static boolean move(Path source, Path target) {
        try {
            Files.move(source, target);
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private static void delete(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to delete queue file " + file, exception);
        }
    }

    private static Number number(Object value) {
        return value instanceof Number number ? number : 0;
    }

    private static String string(Object value) {
        return value == null ? "" : value.toString();
    }
}

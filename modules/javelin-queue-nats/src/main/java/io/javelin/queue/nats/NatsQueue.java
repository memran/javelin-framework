package io.javelin.queue.nats;

import io.javelin.core.Json;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.Queue;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobQueue;
import io.nats.client.Connection;
import io.nats.client.JetStream;
import io.nats.client.JetStreamApiException;
import io.nats.client.JetStreamManagement;
import io.nats.client.Nats;
import io.nats.client.PushSubscribeOptions;
import io.nats.client.api.AckPolicy;
import io.nats.client.api.ConsumerConfiguration;
import io.nats.client.api.StorageType;
import io.nats.client.api.StreamConfiguration;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** JetStream-backed queue. Subjects are namespaced under the configured prefix. */
public final class NatsQueue implements JobQueue {
    private final Connection connection;
    private final JetStream jetStream;
    private final String stream;
    private final String subjectPrefix;

    public NatsQueue(String url, String stream, String subjectPrefix) {
        this.stream = Objects.requireNonNull(stream, "stream");
        this.subjectPrefix = Objects.requireNonNull(subjectPrefix, "subjectPrefix");
        if (stream.isBlank() || subjectPrefix.isBlank()) {
            throw new IllegalArgumentException("stream and subjectPrefix must not be blank");
        }
        try {
            this.connection = Nats.connect(url);
            this.jetStream = connection.jetStream();
            ensureStream(connection.jetStreamManagement());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to connect to NATS JetStream", exception);
        }
    }

    private void ensureStream(JetStreamManagement management) throws Exception {
        try {
            management.getStreamInfo(stream);
        } catch (JetStreamApiException missing) {
            management.addStream(StreamConfiguration.builder()
                    .name(stream)
                    .subjects(subjectPrefix + ".>")
                    .storageType(StorageType.File)
                    .build());
        }
    }

    @Override
    public void publish(Job job) {
        Objects.requireNonNull(job, "job");
        try {
            jetStream.publish(subjectPrefix + "." + job.subject(),
                    Json.stringify(job).getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to publish job to " + job.subject(), exception);
        }
    }

    @Override
    public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) {
        Objects.requireNonNull(subject, "subject");
        Objects.requireNonNull(handler, "handler");
        Objects.requireNonNull(retryPolicy, "retryPolicy");
        String fullSubject = subjectPrefix + "." + subject;
        String durable = durableName(subject);
        String deliverSubject = "_INBOX." + durable;
        ConsumerConfiguration consumer = ConsumerConfiguration.builder()
                .durable(durable)
                .deliverSubject(deliverSubject)
                .ackPolicy(AckPolicy.Explicit)
                .maxDeliver(retryPolicy.maxAttempts())
                .backoff(retryPolicy.backoff())
                .filterSubjects(fullSubject)
                .build();
        try {
            try {
                connection.jetStreamManagement().addOrUpdateConsumer(stream, consumer);
            } catch (JetStreamApiException incompatible) {
                String message = incompatible.getMessage();
                if (message == null || !message.contains("pull consumer to push")) {
                    throw incompatible;
                }
                // A leftover pull consumer with the same durable cannot be updated in place.
                connection.jetStreamManagement().deleteConsumer(stream, durable);
                connection.jetStreamManagement().addOrUpdateConsumer(stream, consumer);
            }

            jetStream.subscribe(fullSubject, connection.createDispatcher(), message -> {
                try {
                    Job incoming = Json.parse(new String(message.getData(), StandardCharsets.UTF_8), Job.class);
                    long delivered = message.isJetStream() ? message.metaData().deliveredCount() : 1;
                    handler.handle(incoming.withAttempts((int) Math.max(1, delivered)));
                    message.ack();
                } catch (Exception exception) {
                    message.nakWithDelay(retryPolicy.backoff());
                }
            }, false, PushSubscribeOptions.builder()
                    .stream(stream)
                    .durable(durable)
                    .deliverSubject(deliverSubject)
                    .bind(true)
                    .build());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to subscribe to " + subject, exception);
        }
    }

    @Override
    public void dispatch(Job job) {
        publish(job);
    }

    @Override
    public void later(Job job, java.time.Duration delay) {
        // JetStream push consumers have no native scheduled delivery; publish immediately.
        publish(job);
    }

    @Override
    public java.util.Optional<ClaimedJob> claim(String workerId, java.time.Duration lease) {
        // NATS pushes jobs to subscribers; there is nothing to poll.
        return java.util.Optional.empty();
    }

    @Override
    public void ack(String id, String leaseToken) {
        // Acknowledged in the push subscriber.
    }

    @Override
    public void fail(String id, String leaseToken, String error, java.time.Duration retryDelay) {
        // Retries are handled by JetStream consumer configuration.
    }

    @Override
    public java.util.List<Job> failedJobs() {
        return java.util.List.of();
    }

    @Override
    public void replay(String failedId) {
        // JetStream retains unacknowledged messages; no separate failed store.
    }

    @Override
    public long pendingCount() {
        return 0;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    static String durableName(String subject) {
        // NATS forbids '.', '*', '>', '\\' and '/' in durable consumer names.
        String name = subject.replaceAll("[^A-Za-z0-9_-]", "-").replaceAll("^-+|-+$", "");
        return name.isBlank() ? "worker" : name;
    }
}

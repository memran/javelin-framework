package io.javelin.queue.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.javelin.core.Json;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobQueue;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** RabbitMQ adapter. Delayed jobs use a TTL queue with dead-lettering to the main queue. */
public final class RabbitMqQueue implements JobQueue {
    private static final String MAIN = "javelin.jobs";
    private static final String DELAY = "javelin.jobs.delay";

    private final Connection connection;
    private final Channel channel;
    private final List<Job> failedJobs = new ArrayList<>();
    private final Map<String, Job> claimedJobs = new java.util.concurrent.ConcurrentHashMap<>();

    public RabbitMqQueue(String uri) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(Objects.requireNonNull(uri, "uri"));
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            channel.queueDeclare(MAIN, true, false, false, Map.of());
            channel.queueDeclare(DELAY, true, false, false, Map.of(
                    "x-dead-letter-exchange", "",
                    "x-dead-letter-routing-key", MAIN));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to connect to RabbitMQ", exception);
        }
    }

    @Override
    public void dispatch(Job job) {
        publish(MAIN, job, null);
    }

    @Override
    public void later(Job job, Duration delay) {
        publish(DELAY, job, String.valueOf(Math.max(1, delay.toMillis())));
    }

    private void publish(String queue, Job job, String expiration) {
        try {
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder()
                    .contentType("application/json");
            if (expiration != null) {
                properties.expiration(expiration);
            }
            channel.basicPublish("", queue, properties.build(),
                    Json.stringify(job).getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to publish job " + job.subject(), exception);
        }
    }

    @Override
    public Optional<ClaimedJob> claim(String workerId, Duration lease) {
        try {
            GetResponse response = channel.basicGet(MAIN, false);
            if (response == null) {
                return Optional.empty();
            }
            Job job = Json.parse(new String(response.getBody(), StandardCharsets.UTF_8), Job.class);
            String token = String.valueOf(response.getEnvelope().getDeliveryTag());
            claimedJobs.put(token, job);
            return Optional.of(new ClaimedJob(job.id(), job, token));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to claim job", exception);
        }
    }

    @Override
    public void ack(String id, String leaseToken) {
        try {
            channel.basicAck(Long.parseLong(leaseToken), false);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to acknowledge job " + id, exception);
        }
    }

    @Override
    public void fail(String id, String leaseToken, String error, Duration retryDelay) {
        Job claimed = claimedJobs.remove(leaseToken);
        try {
            if (retryDelay.isZero() || retryDelay.isNegative()) {
                synchronized (failedJobs) {
                    failedJobs.add(claimed == null
                            ? Job.of("failed", Map.of("id", id, "error", error))
                            : new Job(claimed.id(), "failed", Json.stringify(Map.of(
                                    "payload", claimed.payload(), "error", error)), claimed.attempts() + 1));
                }
            } else {
                Job retry = claimed == null
                        ? Job.of("retry", Map.of("original", id))
                        : claimed.withAttempts(claimed.attempts() + 1);
                publish(DELAY, retry, String.valueOf(Math.max(1, retryDelay.toMillis())));
            }
            channel.basicAck(Long.parseLong(leaseToken), false);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to fail job " + id, exception);
        }
    }

    /** Failed jobs are held in memory for this adapter instance, not in a broker queue. */
    @Override
    public List<Job> failedJobs() {
        synchronized (failedJobs) {
            return List.copyOf(failedJobs);
        }
    }

    @Override
    public void replay(String failedId) {
        synchronized (failedJobs) {
            failedJobs.removeIf(job -> job.id().equals(failedId));
        }
        dispatch(Job.of("replay", Map.of("id", failedId)));
    }

    @Override
    public long pendingCount() {
        try {
            return channel.queueDeclarePassive(MAIN).getMessageCount();
        } catch (Exception exception) {
            return 0;
        }
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
        try {
            channel.close();
            connection.close();
        } catch (Exception exception) {
            Thread.currentThread().interrupt();
        }
    }
}

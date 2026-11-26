package io.javelin.mail;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.HtmlResponse;
import io.javelin.core.View;
import io.javelin.core.ViewRenderer;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.JobHandler;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.mails.Mail;
import io.javelin.mails.MailComposer;
import io.javelin.queues.Queue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MailTest {
    @Test
    void testTransportWritesMessagesToDisk(@TempDir Path dir) throws Exception {
        TestMailTransport transport = new TestMailTransport(dir);
        transport.send(new MailMessage("sender@example.com", List.of("alice@example.com"),
                List.of(), List.of(), "Welcome", "plain", "<h1>Hello</h1>",
                List.of(Attachment.file("report.txt", "text/plain", "report".getBytes(StandardCharsets.UTF_8))),
                "test"));

        Path file = Files.list(dir).findFirst().orElseThrow();
        String content = Files.readString(file);
        assertTrue(content.contains("alice@example.com"));
        assertTrue(content.contains("Welcome"));
        assertTrue(content.contains("report.txt"));
    }

    @Test
    void composerViewAndQueueDispatch(@TempDir Path dir) {
        View.use((template, data) -> new HtmlResponse("<h1>" + data.get("name") + "</h1>"));
        Application app = new Application(new TestConfig(Map.of(
                "mail.driver", "test",
                "mail.test.dir", dir.toString())), key -> Optional.empty());
        app.register(new MailServiceProvider());
        app.boot();

        CapturingQueue previous = capturingQueue();
        try {
            MailComposer composer = Mail.to("alice@example.com")
                    .subject("Welcome")
                    .view("emails/welcome", Map.of("name", "Alice"));
            composer.queue();
            assertEquals("mail.message", previous.job.subject());
            assertTrue(previous.job.payload().contains("<h1>Alice</h1>"));

            MailMessage message = io.javelin.core.Json.parse(previous.job.payload(), MailMessage.class);
            Mail.active().send(message);
            assertTrue(Files.list(dir).findFirst().isPresent());
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        } finally {
            if (previous != null) {
                Queue.use(previous);
            }
        }
    }

    @Test
    void validationAndLoggingMaskSecrets() {
        assertTrue(EmailAddress.isValid("alice@example.com"));
        assertFalse(EmailAddress.isValid("not-an-email"));
        assertEquals("a***@example.com", MailSubmissionLogger.mask("alice@example.com"));
    }

    private static CapturingQueue capturingQueue() {
        CapturingQueue queue = new CapturingQueue();
        Queue.use(queue);
        return queue;
    }

    private record TestConfig(Map<String, String> values) implements Config {
        @Override public Set<String> keys() { return Set.copyOf(values.keySet()); }
        @Override public Optional<String> getString(String key) { return Optional.ofNullable(values.get(key)); }
        @Override public Optional<Integer> getInt(String key) { return getString(key).map(Integer::parseInt); }
        @Override public Optional<Boolean> getBoolean(String key) { return getString(key).map(Boolean::parseBoolean); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }

    private static final class CapturingQueue implements io.javelin.queue.JobQueue {
        private Job job;

        @Override public void dispatch(Job job) { this.job = job; }
        @Override public void later(Job job, Duration delay) { this.job = job; }
        @Override public Optional<ClaimedJob> claim(String workerId, Duration lease) { return Optional.empty(); }
        @Override public void ack(String id, String leaseToken) { }
        @Override public void fail(String id, String leaseToken, String error, Duration retryDelay) { }
        @Override public List<Job> failedJobs() { return List.of(); }
        @Override public void replay(String failedId) { }
        @Override public long pendingCount() { return 0; }
        @Override public void publish(Job job) { dispatch(job); }
        @Override public void subscribe(String subject, JobHandler handler, RetryPolicy retryPolicy) { }
        @Override public void close() { }
    }
}

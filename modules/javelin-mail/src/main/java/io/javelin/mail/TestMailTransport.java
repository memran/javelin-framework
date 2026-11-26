package io.javelin.mail;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

/** Development transport that writes each message to disk instead of sending it. */
public final class TestMailTransport implements MailTransport {
    private final Path directory;
    private final String defaultFrom;

    public TestMailTransport(Path directory) {
        this(directory, "");
    }

    public TestMailTransport(Path directory, String defaultFrom) {
        this.directory = directory.toAbsolutePath().normalize();
        this.defaultFrom = defaultFrom == null ? "" : defaultFrom;
    }

    @Override
    public String name() {
        return "test";
    }

    @Override
    public void send(MailMessage message) throws Exception {
        Files.createDirectories(directory);
        StringBuilder text = new StringBuilder()
                .append("From: ").append(message.from() == null || message.from().isBlank()
                        ? defaultFrom : message.from()).append('\n')
                .append("To: ").append(String.join(", ", message.to())).append('\n')
                .append("Cc: ").append(String.join(", ", message.cc())).append('\n')
                .append("Subject: ").append(message.subject()).append('\n')
                .append("Sent: ").append(Instant.now()).append("\n\n");
        if (message.text() != null) {
            text.append("--- text ---\n").append(message.text()).append("\n\n");
        }
        if (message.html() != null) {
            text.append("--- html ---\n").append(message.html()).append("\n\n");
        }
        message.attachments().forEach(attachment ->
                text.append("--- attachment: ").append(attachment.filename())
                        .append(" (").append(attachment.content().length).append(" bytes) ---\n"));
        Files.writeString(directory.resolve(UUID.randomUUID() + ".mail"), text.toString(), StandardCharsets.UTF_8);
    }
}

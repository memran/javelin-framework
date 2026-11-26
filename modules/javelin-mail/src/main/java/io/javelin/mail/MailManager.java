package io.javelin.mail;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Named transport registry with one default. */
public final class MailManager {
    private final Map<String, MailTransport> transports;
    private final String defaultTransport;
    private final MailSubmissionLogger logger = new MailSubmissionLogger();

    public MailManager(Map<String, MailTransport> transports, String defaultTransport) {
        if (!transports.containsKey(defaultTransport)) {
            throw new IllegalArgumentException("Unknown default mail transport: " + defaultTransport);
        }
        this.transports = Map.copyOf(new LinkedHashMap<>(transports));
        this.defaultTransport = defaultTransport;
    }

    public MailTransport transport(String name) {
        MailTransport transport = transports.get(name);
        if (transport == null) {
            throw new IllegalArgumentException("Unknown mail transport: " + name);
        }
        return transport;
    }

    public MailTransport defaultTransport() {
        return transport(defaultTransport);
    }

    public Set<String> names() {
        return transports.keySet();
    }

    public void send(MailMessage message) throws Exception {
        Objects.requireNonNull(message, "message");
        String name = message.transport() == null || message.transport().isBlank()
                ? defaultTransport
                : message.transport();
        logger.submitted(message, name);
        transport(name).send(message);
    }
}

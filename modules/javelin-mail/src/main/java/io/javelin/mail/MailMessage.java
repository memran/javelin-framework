package io.javelin.mail;

import java.util.ArrayList;
import java.util.List;

/** Serializable message passed to transports and to the queue. */
public record MailMessage(String from, List<String> to, List<String> cc, List<String> bcc,
                          String subject, String text, String html,
                          List<Attachment> attachments, String transport) {
    public MailMessage {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("A mail message needs at least one recipient");
        }
        to = List.copyOf(to);
        cc = cc == null ? List.of() : List.copyOf(cc);
        bcc = bcc == null ? List.of() : List.copyOf(bcc);
        attachments = attachments == null ? List.of() : List.copyOf(attachments);
        subject = subject == null ? "" : subject;
    }

    public List<String> allRecipients() {
        List<String> recipients = new ArrayList<>(to);
        recipients.addAll(cc);
        recipients.addAll(bcc);
        return List.copyOf(recipients);
    }
}

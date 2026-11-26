package io.javelin.mails;

import io.javelin.core.View;
import io.javelin.mail.Attachment;
import io.javelin.mail.EmailAddress;
import io.javelin.mail.MailMessage;
import io.javelin.queues.Queue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Fluent builder for the requested Mail.to(...).view(...).queue() API. */
public final class MailComposer {
    private String from;
    private final List<String> to = new ArrayList<>();
    private final List<String> cc = new ArrayList<>();
    private final List<String> bcc = new ArrayList<>();
    private final List<Attachment> attachments = new ArrayList<>();
    private String subject = "";
    private String text;
    private String html;
    private String transport;

    public MailComposer to(String address) {
        to.add(EmailAddress.requireValid(address));
        return this;
    }

    public MailComposer cc(String address) {
        cc.add(EmailAddress.requireValid(address));
        return this;
    }

    public MailComposer bcc(String address) {
        bcc.add(EmailAddress.requireValid(address));
        return this;
    }

    public MailComposer from(String address) {
        this.from = EmailAddress.requireValid(address);
        return this;
    }

    public MailComposer subject(String subject) {
        this.subject = subject == null ? "" : subject;
        return this;
    }

    public MailComposer text(String text) {
        this.text = text;
        return this;
    }

    public MailComposer html(String html) {
        this.html = html;
        return this;
    }

    /** Renders a Pebble template through the registered ViewRenderer. */
    public MailComposer view(String template, Map<String, Object> data) {
        this.html = new String(View.render(template, data).body(), StandardCharsets.UTF_8);
        return this;
    }

    public MailComposer attach(Attachment attachment) {
        attachments.add(attachment);
        return this;
    }

    public MailComposer transport(String name) {
        this.transport = name;
        return this;
    }

    public MailMessage message() {
        return new MailMessage(from, to, cc, bcc, subject, text, html, attachments, transport);
    }

    /** Sends immediately, or dispatches through the queue when one is active. */
    public void queue() {
        MailMessage message = message();
        if (Queue.active() != null) {
            Queue.dispatch(message);
        } else {
            try {
                Mail.active().send(message);
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to send mail", exception);
            }
        }
    }

    public void send() {
        try {
            Mail.active().send(message());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to send mail", exception);
        }
    }
}

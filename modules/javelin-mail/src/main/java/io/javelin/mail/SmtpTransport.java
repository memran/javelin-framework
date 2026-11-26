package io.javelin.mail;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/** SMTP delivery using Jakarta Mail. */
public final class SmtpTransport implements MailTransport {
    private final SmtpConfig config;

    public SmtpTransport(SmtpConfig config) {
        this.config = config;
    }

    @Override
    public String name() {
        return config.name();
    }

    @Override
    public void send(MailMessage message) throws Exception {
        Session session = Session.getInstance(properties(), authenticator());
        MimeMessage mime = new MimeMessage(session);
        String from = message.from() == null || message.from().isBlank() ? config.from() : message.from();
        if (from == null || from.isBlank()) {
            throw new IllegalStateException("No mail from address configured for transport " + config.name());
        }
        mime.setFrom(new InternetAddress(from));
        mime.setRecipients(Message.RecipientType.TO, addresses(message.to()));
        if (!message.cc().isEmpty()) {
            mime.setRecipients(Message.RecipientType.CC, addresses(message.cc()));
        }
        if (!message.bcc().isEmpty()) {
            mime.setRecipients(Message.RecipientType.BCC, addresses(message.bcc()));
        }
        mime.setSubject(message.subject(), StandardCharsets.UTF_8.name());
        mime.setContent(content(message));
        mime.saveChanges();
        Transport.send(mime, addresses(message.allRecipients()));
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", config.host());
        properties.put("mail.smtp.port", String.valueOf(config.port()));
        properties.put("mail.smtp.auth", String.valueOf(config.auth()));
        properties.put("mail.smtp.starttls.enable", String.valueOf(config.starttls()));
        properties.put("mail.smtp.connectiontimeout", String.valueOf(config.timeoutMs()));
        properties.put("mail.smtp.timeout", String.valueOf(config.timeoutMs()));
        if (config.ssl()) {
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.smtp.socketFactory.port", String.valueOf(config.port()));
        }
        return properties;
    }

    private Authenticator authenticator() {
        if (!config.auth()) {
            return null;
        }
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.username(), config.password());
            }
        };
    }

    private MimeMultipart content(MailMessage message) throws Exception {
        MimeMultipart body = new MimeMultipart("alternative");
        if (message.text() != null && !message.text().isBlank()) {
            MimeBodyPart text = new MimeBodyPart();
            text.setText(message.text(), StandardCharsets.UTF_8.name());
            body.addBodyPart(text);
        }
        if (message.html() != null && !message.html().isBlank()) {
            MimeBodyPart html = new MimeBodyPart();
            html.setContent(message.html(), "text/html; charset=UTF-8");
            body.addBodyPart(html);
        }
        if (message.attachments().isEmpty()) {
            return body;
        }
        MimeMultipart mixed = new MimeMultipart("mixed");
        MimeBodyPart wrapper = new MimeBodyPart();
        wrapper.setContent(body);
        mixed.addBodyPart(wrapper);
        for (Attachment attachment : message.attachments()) {
            MimeBodyPart part = new MimeBodyPart();
            part.setFileName(attachment.filename());
            part.setDataHandler(new jakarta.activation.DataHandler(
                    new ByteArrayDataSource(attachment.content(), attachment.contentType())));
            if (attachment.inline()) {
                part.setContentID("<" + attachment.contentId() + ">");
                part.setDisposition(MimeBodyPart.INLINE);
            } else {
                part.setDisposition(MimeBodyPart.ATTACHMENT);
            }
            mixed.addBodyPart(part);
        }
        return mixed;
    }

    private static InternetAddress[] addresses(List<String> values) throws Exception {
        InternetAddress[] result = new InternetAddress[values.size()];
        for (int index = 0; index < values.size(); index++) {
            result[index] = new InternetAddress(values.get(index));
        }
        return result;
    }
}

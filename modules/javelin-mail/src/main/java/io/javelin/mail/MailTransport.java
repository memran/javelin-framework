package io.javelin.mail;

/** Explicit mail delivery backend. */
public interface MailTransport {
    String name();

    void send(MailMessage message) throws Exception;
}

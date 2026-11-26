package io.javelin.mails;

import io.javelin.mail.MailManager;

import java.util.Objects;

/** Static entry point for composing mail. */
public final class Mail {
    private static volatile MailManager active;

    private Mail() {
    }

    public static void use(MailManager manager) {
        active = Objects.requireNonNull(manager, "manager");
    }

    public static MailManager active() {
        if (active == null) {
            throw new IllegalStateException("Mail is not configured; register MailServiceProvider");
        }
        return active;
    }

    public static MailComposer to(String address) {
        return new MailComposer().to(address);
    }
}

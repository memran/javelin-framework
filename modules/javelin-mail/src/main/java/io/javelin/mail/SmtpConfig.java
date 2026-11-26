package io.javelin.mail;

import io.javelin.core.Config;

/** Connection settings for one named SMTP server. */
public record SmtpConfig(String name, String host, int port, String username, String password,
                         boolean auth, boolean starttls, boolean ssl, String from, int timeoutMs) {
    public static SmtpConfig from(Config config, String name) {
        String prefix = "mail.smtp." + name + ".";
        return new SmtpConfig(
                name,
                config.getString(prefix + "host", "localhost"),
                config.getInt(prefix + "port", 587),
                config.getString(prefix + "username", ""),
                config.getString(prefix + "password", ""),
                config.getBoolean(prefix + "auth").orElse(!config.getString(prefix + "username", "").isEmpty()),
                config.getBoolean(prefix + "starttls").orElse(true),
                config.getBoolean(prefix + "ssl").orElse(false),
                config.getString(prefix + "from", config.getString("mail.from", "")),
                config.getInt(prefix + "timeout_ms", 5000));
    }
}

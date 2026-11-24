package io.javelin.security;

import io.javelin.core.Config;

/** Session cookie and lifetime settings. */
public record SessionConfig(String cookieName, int ttlSeconds, boolean secure, boolean httpOnly, String sameSite) {
    public static SessionConfig from(Config config) {
        return new SessionConfig(
                config.getString("session.cookie_name", "javelin_session"),
                config.getInt("session.ttl_seconds", 1800),
                config.getBoolean("session.secure").orElse(true),
                config.getBoolean("session.http_only").orElse(true),
                config.getString("session.same_site", "Lax"));
    }
}

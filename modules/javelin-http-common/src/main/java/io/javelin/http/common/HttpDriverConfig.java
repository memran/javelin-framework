package io.javelin.http.common;

import io.javelin.core.Config;

public final class HttpDriverConfig {
    private HttpDriverConfig() {
    }

    public static String driver(Config config) {
        String value = config.getString("javelin.starter.http-driver")
                .orElseGet(() -> config.getString("server.driver", "netty"));
        return value == null || value.isBlank() ? "netty" : value.trim();
    }
}

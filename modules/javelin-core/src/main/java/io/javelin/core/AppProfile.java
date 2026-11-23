package io.javelin.core;

import java.util.Locale;

public enum AppProfile {
    DEV,
    PROD;

    public static final String ENV_KEY = "APP_ENV";
    public static final String CONFIG_KEY = "app.env";
    public static final String DEFAULT = "local";

    public static AppProfile from(Env env, Config config) {
        String value = env.get(ENV_KEY)
                .orElseGet(() -> config.getString(CONFIG_KEY, DEFAULT));
        return parse(value);
    }

    public static AppProfile parse(String value) {
        if (value == null || value.isBlank()) {
            return DEV;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        return normalized.equals("prod") || normalized.equals("production") ? PROD : DEV;
    }

    public boolean isDev() {
        return this == DEV;
    }

    public boolean isProd() {
        return this == PROD;
    }

    public String value() {
        return name().toLowerCase(Locale.ROOT);
    }
}

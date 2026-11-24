package io.javelin.security;

import io.javelin.core.Config;

import java.util.List;

/** CORS settings read from explicit {@code cors.*} keys. */
public record CorsConfig(List<String> allowedOrigins, List<String> allowedMethods,
                         List<String> allowedHeaders, boolean allowCredentials, int maxAgeSeconds) {
    public static final List<String> DEFAULT_METHODS = List.of("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    public static final List<String> DEFAULT_HEADERS = List.of(
            "Content-Type", "Authorization", "X-CSRF-Token", "X-Request-ID", "X-Correlation-ID");

    public static CorsConfig from(Config config) {
        return new CorsConfig(
                List.copyOf(config.getStringList("cors.allowed_origins")),
                orDefault(config.getStringList("cors.allowed_methods"), DEFAULT_METHODS),
                orDefault(config.getStringList("cors.allowed_headers"), DEFAULT_HEADERS),
                config.getBoolean("cors.allow_credentials").orElse(false),
                config.getInt("cors.max_age_seconds", 600));
    }

    public boolean enabled() {
        return !allowedOrigins.isEmpty();
    }

    private static List<String> orDefault(List<String> value, List<String> fallback) {
        return value.isEmpty() ? fallback : List.copyOf(value);
    }
}

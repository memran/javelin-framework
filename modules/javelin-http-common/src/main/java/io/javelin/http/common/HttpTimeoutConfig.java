package io.javelin.http.common;

import java.time.Duration;
import java.util.Objects;

/** Server-side timeouts applied by HTTP adapters. */
public record HttpTimeoutConfig(Duration request, Duration idle) {
    public static final HttpTimeoutConfig DEFAULT =
            new HttpTimeoutConfig(Duration.ofSeconds(30), Duration.ofSeconds(60));

    public HttpTimeoutConfig {
        request = Objects.requireNonNull(request, "request");
        idle = Objects.requireNonNull(idle, "idle");
        if (request.isZero() || request.isNegative()) {
            throw new IllegalArgumentException("request timeout must be positive");
        }
        if (idle.isZero() || idle.isNegative()) {
            throw new IllegalArgumentException("idle timeout must be positive");
        }
    }
}

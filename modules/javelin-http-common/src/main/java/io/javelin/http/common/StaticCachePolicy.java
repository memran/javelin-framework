package io.javelin.http.common;

/** Cache-Control policy for static assets. Set {@code immutable} only for fingerprinted files. */
public record StaticCachePolicy(int maxAgeSeconds, boolean immutable) {
    public static final StaticCachePolicy DEFAULT = new StaticCachePolicy(3600, false);

    public StaticCachePolicy {
        if (maxAgeSeconds < 0) {
            throw new IllegalArgumentException("maxAgeSeconds must not be negative");
        }
        if (immutable && maxAgeSeconds == 0) {
            throw new IllegalArgumentException("immutable assets require a positive maxAgeSeconds");
        }
    }

    public String headerValue() {
        return immutable
                ? "public, max-age=" + maxAgeSeconds + ", immutable"
                : "public, max-age=" + maxAgeSeconds;
    }
}

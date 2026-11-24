package io.javelin.http.common;

import io.javelin.core.Config;

import java.nio.file.Path;
import java.util.List;

public record HttpAdapterConfig(String host, int port, Path staticRoot, List<String> trustedProxies,
                                int maxRequestBytes, StaticCachePolicy staticCachePolicy,
                                HttpTimeoutConfig timeouts) {
    public static final int DEFAULT_MAX_REQUEST_BYTES = 1_048_576;

    public HttpAdapterConfig(String host, int port, Path staticRoot, List<String> trustedProxies, int maxRequestBytes) {
        this(host, port, staticRoot, trustedProxies, maxRequestBytes, StaticCachePolicy.DEFAULT);
    }

    public HttpAdapterConfig(String host, int port, Path staticRoot, List<String> trustedProxies, int maxRequestBytes,
                             StaticCachePolicy staticCachePolicy) {
        this(host, port, staticRoot, trustedProxies, maxRequestBytes, staticCachePolicy, HttpTimeoutConfig.DEFAULT);
    }

    public static HttpAdapterConfig from(Config config, Path root) {
        return new HttpAdapterConfig(
                config.getString("server.host", "127.0.0.1"),
                config.getInt("server.port", 8080),
                root.resolve(config.getString("server.static_dir", "public")),
                config.getStringList("security.trusted_proxies"),
                config.getInt("security.max_request_bytes", DEFAULT_MAX_REQUEST_BYTES),
                new StaticCachePolicy(
                        config.getInt("server.static_cache_seconds", StaticCachePolicy.DEFAULT.maxAgeSeconds()),
                        config.getBoolean("server.static_cache_immutable").orElse(false)
                ),
                new HttpTimeoutConfig(
                        java.time.Duration.ofSeconds(
                                config.getInt("server.request_timeout_seconds",
                                        (int) HttpTimeoutConfig.DEFAULT.request().toSeconds())),
                        java.time.Duration.ofSeconds(
                                config.getInt("server.idle_timeout_seconds",
                                        (int) HttpTimeoutConfig.DEFAULT.idle().toSeconds()))
                )
        );
    }
}

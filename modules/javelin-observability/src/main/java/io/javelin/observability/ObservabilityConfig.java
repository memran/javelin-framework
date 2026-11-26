package io.javelin.observability;

import io.javelin.core.Config;

/** Settings for the observability endpoints. */
public record ObservabilityConfig(boolean enabled, String healthPath, String readyPath,
                                  String metricsPath, String tracing) {
    public static ObservabilityConfig from(Config config) {
        return new ObservabilityConfig(
                config.getBoolean("observability.enabled").orElse(true),
                config.getString("observability.health_path", "/health"),
                config.getString("observability.ready_path", "/ready"),
                config.getString("observability.metrics_path", "/metrics"),
                config.getString("observability.tracing", "none"));
    }
}

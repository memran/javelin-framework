package io.javelin.http.common;

import io.javelin.core.Config;
import io.javelin.core.HttpKernel;

/** Applies the configured static-asset precedence to an HTTP kernel. */
public final class StaticFallbackConfig {
    private StaticFallbackConfig() {
    }

    public static HttpKernel apply(HttpKernel kernel, Config config) {
        return config.getBoolean("server.static_after_routes").orElse(false)
                ? kernel.staticAfterRoutes()
                : kernel;
    }
}

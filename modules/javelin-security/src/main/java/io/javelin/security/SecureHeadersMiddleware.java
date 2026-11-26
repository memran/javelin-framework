package io.javelin.security;

import io.javelin.core.Config;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;

/** Secure response headers. HSTS and CSP are opt-in via config. */
public final class SecureHeadersMiddleware implements Middleware {
    private final boolean hsts;
    private final String hstsValue;
    private final String csp;

    public SecureHeadersMiddleware() {
        this(null);
    }

    public SecureHeadersMiddleware(Config config) {
        boolean enabled = config != null && config.getBoolean("security.hsts.enabled").orElse(false);
        int maxAge = config == null ? 31_536_000 : config.getInt("security.hsts.max_age_seconds", 31_536_000);
        this.hsts = enabled;
        this.hstsValue = "max-age=" + maxAge + "; includeSubDomains";
        this.csp = config == null ? "" : config.getString("security.csp", "");
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        Response response = next.handle(request);
        Map<String, String> headers = new LinkedHashMap<>(response.headers());
        headers.putIfAbsent("X-Content-Type-Options", "nosniff");
        headers.putIfAbsent("X-Frame-Options", "DENY");
        headers.putIfAbsent("Referrer-Policy", "no-referrer");
        headers.putIfAbsent("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
        if (hsts && request.header("X-Forwarded-Proto").map("https"::equals).orElse(true)) {
            headers.putIfAbsent("Strict-Transport-Security", hstsValue);
        }
        if (!csp.isBlank()) {
            headers.putIfAbsent("Content-Security-Policy", csp);
        }
        return response.withHeaders(headers);
    }
}

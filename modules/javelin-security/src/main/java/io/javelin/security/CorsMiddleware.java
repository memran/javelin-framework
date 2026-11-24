package io.javelin.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

/** Explicit CORS handling: exact origins (or {@code *}), preflight and credential policy. */
public final class CorsMiddleware implements Middleware {
    private final CorsConfig config;

    public CorsMiddleware(CorsConfig config) {
        this.config = config;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        String origin = request.header("Origin").orElse("");
        if (origin.isBlank() || !config.enabled()) {
            return next.handle(request);
        }
        if (!allowed(origin)) {
            if (isPreflight(request)) {
                return Response.text("Origin not allowed", 403);
            }
            return next.handle(request);
        }
        if (isPreflight(request)) {
            return preflight(origin);
        }
        Response response = next.handle(request);
        return withHeaders(response, origin);
    }

    private boolean isPreflight(Request request) {
        return request.method() == HttpMethod.OPTIONS
                && request.header("Access-Control-Request-Method").isPresent();
    }

    private boolean allowed(String origin) {
        return config.allowedOrigins().contains("*") || config.allowedOrigins().contains(origin);
    }

    private Response preflight(String origin) {
        String methods = String.join(", ", config.allowedMethods());
        String headers = String.join(", ", config.allowedHeaders());
        Response response = Response.noContent()
                .withHeader("Access-Control-Allow-Origin", origin)
                .withHeader("Access-Control-Allow-Methods", methods)
                .withHeader("Access-Control-Allow-Headers", headers)
                .withHeader("Access-Control-Max-Age", String.valueOf(config.maxAgeSeconds()))
                .withHeader("Vary", "Origin");
        if (config.allowCredentials()) {
            response = response.withHeader("Access-Control-Allow-Credentials", "true");
        }
        return response;
    }

    private Response withHeaders(Response response, String origin) {
        String allowOrigin = config.allowedOrigins().contains("*") && !config.allowCredentials()
                ? "*"
                : origin;
        Response updated = response
                .withHeader("Access-Control-Allow-Origin", allowOrigin)
                .withHeader("Vary", "Origin");
        if (config.allowCredentials()) {
            updated = updated.withHeader("Access-Control-Allow-Credentials", "true");
        }
        return updated;
    }
}

package io.javelin.core.security;

import io.javelin.core.Response;

import java.nio.charset.StandardCharsets;
import java.util.Map;

final class SecurityResponses {
    private SecurityResponses() {
    }

    static Response unauthorized() {
        return new Response(401, Map.of(
                "WWW-Authenticate", "Bearer",
                "Content-Type", "text/plain; charset=utf-8"
        ), "Unauthorized".getBytes(StandardCharsets.UTF_8));
    }

    static Response forbidden() {
        return Response.text("Forbidden", 403);
    }
}

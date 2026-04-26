package io.javelin.security;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SecureHeadersMiddleware implements Middleware {
    @Override
    public Response handle(Request request, Next next) throws Exception {
        Response response = next.handle(request);
        Map<String, String> headers = new LinkedHashMap<>(response.headers());
        headers.putIfAbsent("X-Content-Type-Options", "nosniff");
        headers.putIfAbsent("X-Frame-Options", "DENY");
        headers.putIfAbsent("Referrer-Policy", "no-referrer");
        headers.putIfAbsent("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
        return new Response(response.status(), headers, response.body());
    }
}

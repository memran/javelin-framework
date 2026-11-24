package io.javelin.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Synchronizer-token CSRF protection backed by the session. Safe methods issue a
 * token; unsafe methods must echo it in the X-CSRF-Token header or _token field.
 */
public final class CsrfMiddleware implements Middleware {
    public static final String TOKEN_KEY = "javelin.csrf";
    public static final String HEADER = "X-CSRF-Token";
    public static final String FIELD = "_token";

    @Override
    public Response handle(Request request, Next next) throws Exception {
        if (request.header("Authorization").map(value -> value.toLowerCase().startsWith("bearer ")).orElse(false)) {
            return next.handle(request);
        }
        Session session = request.session().orElse(null);
        if (session == null) {
            return Response.text("CSRF protection requires a session", 403);
        }
        String expected = token(session);
        Request attached = request.withAttribute(TOKEN_KEY, expected);
        if (safe(request.method())) {
            return next.handle(attached).withHeader(HEADER, expected);
        }
        String supplied = request.header(HEADER).orElseGet(() -> request.input().text(FIELD).orElse(""));
        if (supplied.isBlank() || !MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8), supplied.getBytes(StandardCharsets.UTF_8))) {
            return Response.text("Invalid CSRF token", 403);
        }
        return next.handle(attached).withHeader(HEADER, expected);
    }

    private static boolean safe(HttpMethod method) {
        return method == HttpMethod.GET || method == HttpMethod.HEAD || method == HttpMethod.OPTIONS;
    }

    private static String token(Session session) {
        Object existing = session.get(TOKEN_KEY);
        if (existing instanceof String value && !value.isBlank()) {
            return value;
        }
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String generated = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        session.put(TOKEN_KEY, generated);
        return generated;
    }
}

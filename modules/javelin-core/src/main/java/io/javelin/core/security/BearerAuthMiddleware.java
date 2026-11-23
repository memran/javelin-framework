package io.javelin.core.security;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.Objects;

/** Requires a valid Bearer token and makes the resolved principal available to later middleware. */
public final class BearerAuthMiddleware implements Middleware {
    public static final String PRINCIPAL_ATTRIBUTE = "javelin.principal";

    private final BearerTokenResolver resolver;

    public BearerAuthMiddleware(BearerTokenResolver resolver) {
        this.resolver = Objects.requireNonNull(resolver, "resolver");
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        String token = tokenFrom(request.header("Authorization").orElse(""));
        if (token == null) {
            return SecurityResponses.unauthorized();
        }
        Authenticated principal = resolver.resolve(token).orElse(null);
        if (principal == null) {
            return SecurityResponses.unauthorized();
        }
        return next.handle(request.withAttribute(PRINCIPAL_ATTRIBUTE, principal));
    }

    private static String tokenFrom(String header) {
        int space = header.indexOf(' ');
        if (space <= 0 || !header.substring(0, space).equalsIgnoreCase("Bearer")) {
            return null;
        }
        String token = header.substring(space + 1).trim();
        return token.isEmpty() ? null : token;
    }
}

package io.javelin.security;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;

/** Makes a session principal available to the core authorization middleware. */
public final class SessionAuthMiddleware implements Middleware {
    private final SessionPrincipalResolver resolver;

    public SessionAuthMiddleware(SessionPrincipalResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        if (request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).isPresent()) {
            return next.handle(request);
        }
        Session session = request.session().orElse(null);
        if (session == null) {
            return next.handle(request);
        }
        Authenticated principal = resolver.resolve(session).orElse(null);
        if (principal == null) {
            return next.handle(request);
        }
        return next.handle(request.withAttribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE, principal));
    }
}

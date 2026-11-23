package io.javelin.core.security;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

/** Rejects requests that have not passed an authentication middleware. */
public final class RequiresAuthenticationMiddleware implements Middleware {
    @Override
    public Response handle(Request request, Next next) throws Exception {
        Object principal = request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElse(null);
        if (!(principal instanceof Authenticated)) {
            return SecurityResponses.unauthorized();
        }
        return next.handle(request);
    }
}

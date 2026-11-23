package io.javelin.core.security;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.Objects;

/** Requires an authenticated principal that was granted the given permission. */
public final class RequiresPermissionMiddleware implements Middleware {
    private final String permission;

    public RequiresPermissionMiddleware(String permission) {
        this.permission = Objects.requireNonNull(permission, "permission");
        if (permission.isBlank()) {
            throw new IllegalArgumentException("permission must not be blank");
        }
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        Object value = request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElse(null);
        if (!(value instanceof Authenticated principal)) {
            return SecurityResponses.unauthorized();
        }
        if (!principal.hasPermission(permission)) {
            return SecurityResponses.forbidden();
        }
        return next.handle(request);
    }
}

package io.javelin.core.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AuthorizationMiddlewareTest {
    @Test
    void authenticationGuardRequiresAPrincipal() throws Exception {
        RequiresAuthenticationMiddleware guard = new RequiresAuthenticationMiddleware();
        Middleware.Next ok = next -> Response.text("ok");

        assertEquals(401, guard.handle(request(), ok).status());
        assertEquals(200, guard.handle(authenticated("alice", Set.of()), ok).status());
    }

    @Test
    void permissionGuardDistinguishesUnauthenticatedForbiddenAndAllowed() throws Exception {
        RequiresPermissionMiddleware guard = new RequiresPermissionMiddleware("users.write");
        Middleware.Next ok = next -> Response.text("ok");

        assertEquals(401, guard.handle(request(), ok).status());
        assertEquals(403, guard.handle(authenticated("alice", Set.of("users.read")), ok).status());
        assertEquals(200, guard.handle(authenticated("alice", Set.of("users.write")), ok).status());
    }

    @Test
    void rejectsBlankPermissions() {
        assertThrows(IllegalArgumentException.class, () -> new RequiresPermissionMiddleware("  "));
    }

    private static Request request() {
        return new Request(HttpMethod.GET, "/", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static Request authenticated(String subject, Set<String> permissions) {
        return request().withAttribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE,
                new Authenticated(subject, permissions));
    }
}

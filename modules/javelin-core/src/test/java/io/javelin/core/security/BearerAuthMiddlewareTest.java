package io.javelin.core.security;

import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Route;
import io.javelin.core.Router;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BearerAuthMiddlewareTest {
    @Test
    void rejectsMissingMalformedAndUnknownTokens() throws Exception {
        BearerAuthMiddleware middleware = new BearerAuthMiddleware(this::resolve);

        assertEquals(401, middleware.handle(request(null), next -> Response.text("ok")).status());
        assertEquals(401, middleware.handle(request("Basic abc"), next -> Response.text("ok")).status());
        assertEquals(401, middleware.handle(request("Bearer "), next -> Response.text("ok")).status());
        assertEquals(401, middleware.handle(request("Bearer unknown"), next -> Response.text("ok")).status());
    }

    @Test
    void unauthorizedResponsesAskForBearerCredentials() throws Exception {
        BearerAuthMiddleware middleware = new BearerAuthMiddleware(this::resolve);

        Response response = middleware.handle(request(null), next -> Response.text("ok"));

        assertEquals("Bearer", response.headers().get("WWW-Authenticate"));
        assertEquals("Unauthorized", new String(response.body(), StandardCharsets.UTF_8));
    }

    @Test
    void passesTheResolvedPrincipalToLaterHandlers() throws Exception {
        BearerAuthMiddleware middleware = new BearerAuthMiddleware(this::resolve);

        Response response = middleware.handle(request("Bearer good"), passed -> {
            Object principal = passed.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElseThrow();
            assertTrue(principal instanceof Authenticated);
            assertEquals("alice", ((Authenticated) principal).subject());
            return Response.text("ok");
        });

        assertEquals(200, response.status());
    }

    @Test
    void kernelAppliesAuthenticationAndPermissionMiddlewareToARoute() {
        Router router = new Router();
        router.add(Route.get("/users/{id}",
                        request -> Response.text(request.param("id").orElseThrow() + ":"
                                + ((Authenticated) request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElseThrow()).subject()))
                .middleware(new BearerAuthMiddleware(this::resolve))
                .middleware(new RequiresPermissionMiddleware("users.read")));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        assertEquals(200, kernel.handle(request("Bearer good", "/users/42")).status());
        assertEquals("42:alice", new String(kernel.handle(request("Bearer good", "/users/42")).body(), StandardCharsets.UTF_8));
        assertEquals(401, kernel.handle(request(null, "/users/42")).status());
        assertEquals(403, kernel.handle(request("Bearer readonly", "/users/42")).status());
    }

    private Optional<Authenticated> resolve(String token) {
        return switch (token) {
            case "good" -> Optional.of(new Authenticated("alice", Set.of("users.read")));
            case "readonly" -> Optional.of(new Authenticated("bob", Set.of()));
            default -> Optional.empty();
        };
    }

    private static Request request(String authorization) {
        return request(authorization, "/");
    }

    private static Request request(String authorization, String path) {
        Map<String, List<String>> headers = authorization == null
                ? Map.of()
                : Map.of("Authorization", List.of(authorization));
        return new Request(HttpMethod.GET, path, headers, Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }
}

package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RouterTest {
    @Test
    void dispatchesRouteParametersThroughKernel() {
        Router router = new Router();
        router.get("/users/{id}", request -> Json.ok(Map.of("id", request.param("id").orElseThrow())));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        Response response = kernel.handle(new Request(HttpMethod.GET, "/users/42", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));

        assertEquals(200, response.status());
        assertEquals("{\"id\":\"42\"}", new String(response.body()));
    }

    @Test
    void executesMiddlewareInOrder() {
        Router router = new Router();
        StringBuilder calls = new StringBuilder();
        router.middleware((request, next) -> {
            calls.append("a");
            Response response = next.handle(request);
            calls.append("c");
            return response;
        });
        router.get("/", request -> {
            calls.append("b");
            return Response.text("ok");
        });

        new HttpKernel(router, new DefaultExceptionHandler())
                .handle(new Request(HttpMethod.GET, "/", Map.of(), Map.of(), Map.of(), new byte[0], ""));

        assertEquals("abc", calls.toString());
        assertEquals(List.of("/"), router.routes().stream().map(Route::path).toList());
    }

    @Test
    void headRequestsFallBackToGetRoutes() {
        Router router = new Router();
        router.get("/health", request -> Response.text("ok"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        Response response = kernel.handle(new Request(
                HttpMethod.HEAD, "/health", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));

        assertEquals(200, response.status());
    }

    @Test
    void constrainsRouteParametersWithAnInlineRegex() {
        Router router = new Router();
        router.get("/users/{id:[0-9]+}", request -> Json.ok(Map.of("id", request.param("id").orElseThrow())));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        Response numeric = kernel.handle(new Request(
                HttpMethod.GET, "/users/42", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));
        Response nonNumeric = kernel.handle(new Request(
                HttpMethod.GET, "/users/abc", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));

        assertEquals(200, numeric.status());
        assertEquals("{\"id\":\"42\"}", new String(numeric.body(), StandardCharsets.UTF_8));
        assertEquals(404, nonNumeric.status());
    }

    @Test
    void supportsSeveralConstrainedParametersWithBracedQuantifiers() {
        Router router = new Router();
        router.get("/files/{code:[A-Z]{3}}/{revision:[0-9]{1,3}}",
                request -> Response.text(request.param("code").orElseThrow() + "-" + request.param("revision").orElseThrow()));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());

        Response valid = kernel.handle(new Request(
                HttpMethod.GET, "/files/ABC/7", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));
        Response invalid = kernel.handle(new Request(
                HttpMethod.GET, "/files/abc/7", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1"));

        assertEquals(200, valid.status());
        assertEquals("ABC-7", new String(valid.body(), StandardCharsets.UTF_8));
        assertEquals(404, invalid.status());
    }

    @Test
    void rejectsInvalidParameterConstraintsAtRegistration() {
        Router router = new Router();

        assertThrows(IllegalArgumentException.class,
                () -> router.get("/x/{id:}", request -> Response.text("ok")));
        assertThrows(IllegalArgumentException.class,
                () -> router.get("/x/{id:[}", request -> Response.text("ok")));
    }

    @Test
    void generatesUrlsForNamedRoutes() {
        Router router = new Router();
        router.add(Route.get("/users/{id:[0-9]+}", request -> Response.text("user"))
                .name("users.show"));

        Route route = router.named("users.show").orElseThrow();

        assertEquals(List.of("id"), route.parameterNames());
        assertEquals("/users/42", router.path("users.show", Map.of("id", "42")));
        assertEquals("/users/a%20b", router.path("users.show", Map.of("id", "a b")));
        assertThrows(IllegalArgumentException.class,
                () -> router.path("users.show", Map.of()));
        assertThrows(IllegalArgumentException.class,
                () -> router.path("missing", Map.of()));
    }

    @Test
    void rejectsDuplicateRouteNames() {
        Router router = new Router();
        router.add(Route.get("/a", request -> Response.text("a")).name("same"));

        assertThrows(IllegalArgumentException.class,
                () -> router.add(Route.get("/b", request -> Response.text("b")).name("same")));
    }

    @Test
    void rejectsDuplicateMethodAndPathRegistrations() {
        Router router = new Router();
        router.get("/users", request -> Response.text("first"));

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> router.get("/users", request -> Response.text("second")));

        assertTrue(error.getMessage().contains("Duplicate route registration"));
        assertEquals(1, router.routes().size());
    }

    @Test
    void rejectsDuplicatePatternRegistrationsButAllowsDifferentMethods() {
        Router router = new Router();
        router.get("/users/{id}", request -> Response.text("first"));
        router.post("/users/{id}", request -> Response.text("post"));

        assertThrows(IllegalArgumentException.class,
                () -> router.get("/users/{id}", request -> Response.text("second")));
        assertEquals(List.of("/users/{id}", "/users/{id}"), router.routes().stream().map(Route::path).toList());
    }

    @Test
    void reportsAllowedMethodsIncludingHeadForGetRoutes() {
        Router router = new Router();
        router.get("/users/{id}", request -> Response.text("get"));
        router.post("/users/{id}", request -> Response.text("post"));

        assertEquals(List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD), router.allowedMethods("/users/42"));
        assertEquals(List.of(), router.allowedMethods("/missing"));
    }

    @Test
    void routeFacadeCreatesBuilders() {
        assertNotNull(Routes.get("/", request -> Response.text("ok")).build());
        assertNotNull(Routes.post("/", request -> Response.text("ok")).build());
    }

    @Test
    void routeFacadeCanGroupRoutes() {
        Router router = new Router();
        Routes.group(router, "/admin", admin -> Routes.add(admin, Routes.get("/", request -> Response.text("ok"))));

        assertEquals(List.of("/admin"), router.routes().stream().map(Route::path).toList());
    }

    @Test
    void returnsHtmlErrorPageForMissingRoutesWhenBrowserRequestsHtml() {
        Router router = new Router();
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request request = new Request(
                HttpMethod.GET,
                "/missing",
                Map.of("Accept", List.of("text/html")),
                Map.of(),
                Map.of(),
                new byte[0],
                "127.0.0.1"
        );

        Response response = kernel.handle(request);

        assertEquals(404, response.status());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
        assertTrue(new String(response.body(), StandardCharsets.UTF_8).contains("Not Found"));
    }
}

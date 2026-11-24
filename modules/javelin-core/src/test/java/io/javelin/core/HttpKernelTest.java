package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class HttpKernelTest {
    @Test
    void staticFallbackPassesThroughGlobalMiddleware() {
        Router router = new Router();
        router.middleware((request, next) -> {
            Response response = next.handle(request);
            return new Response(response.status(), Map.of("X-Middleware", "yes"), response.body());
        });
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler())
                .withStaticFallback(request -> Optional.of(Response.text("static")));
        Request request = new Request(
                HttpMethod.GET, "/asset.txt", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = kernel.handle(request);

        assertEquals(200, response.status());
        assertEquals("yes", response.headers().get("X-Middleware"));
    }

    @Test
    void returns405WithAllowHeaderWhenOnlyOtherMethodsMatchThePath() {
        Router router = new Router();
        router.get("/users/{id}", request -> Response.text("get"));
        router.post("/users/{id}", request -> Response.text("post"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request request = new Request(
                HttpMethod.PUT, "/users/42", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = kernel.handle(request);

        assertEquals(405, response.status());
        assertEquals("GET, POST, HEAD", response.headers().get("Allow"));
        assertEquals("Method Not Allowed", new String(response.body(), java.nio.charset.StandardCharsets.UTF_8));
    }

    @Test
    void answersOptionsWith204AndAllowForKnownPaths() {
        Router router = new Router();
        router.get("/users", request -> Response.text("users"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request request = new Request(
                HttpMethod.OPTIONS, "/users", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = kernel.handle(request);

        assertEquals(204, response.status());
        assertEquals("GET, HEAD", response.headers().get("Allow"));
        assertEquals(0, response.body().length);
    }

    @Test
    void automaticOptionsReplyPassesThroughGlobalMiddleware() {
        Router router = new Router();
        router.get("/users", request -> Response.text("users"));
        router.middleware((request, next) -> next.handle(request).withHeader("X-Middleware", "seen"));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request request = new Request(
                HttpMethod.OPTIONS, "/users", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = kernel.handle(request);

        assertEquals("seen", response.headers().get("X-Middleware"));
        assertEquals("GET, HEAD", response.headers().get("Allow"));
    }

    @Test
    void returns404WhenNoMethodMatchesThePath() {
        HttpKernel kernel = new HttpKernel(new Router(), new DefaultExceptionHandler());
        Request request = new Request(
                HttpMethod.PUT, "/missing", Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");

        assertEquals(404, kernel.handle(request).status());
    }
}

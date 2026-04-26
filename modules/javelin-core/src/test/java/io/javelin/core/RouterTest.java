package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

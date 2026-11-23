package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DefaultExceptionHandlerTest {
    @Test
    void returnsHtmlErrorPageForBrowserRequests() {
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
        Request request = new Request(
                HttpMethod.GET,
                "/boom",
                Map.of("Accept", List.of("text/html")),
                Map.of(),
                Map.of(),
                new byte[0],
                "127.0.0.1"
        );

        Response response = handler.handle(new IllegalStateException("boom"), request);

        assertEquals(500, response.status());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
        assertTrue(new String(response.body(), StandardCharsets.UTF_8).contains("Internal Server Error"));
    }

    @Test
    void returnsJsonForJsonClients() {
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
        Request request = new Request(
                HttpMethod.GET,
                "/boom",
                Map.of("Accept", List.of("application/json")),
                Map.of(),
                Map.of(),
                new byte[0],
                "127.0.0.1"
        );

        Response response = handler.handle(new IllegalStateException("boom"), request);

        assertEquals(500, response.status());
        assertEquals("application/json; charset=utf-8", response.headers().get("Content-Type"));
        assertTrue(new String(response.body(), StandardCharsets.UTF_8).contains("Internal Server Error"));
    }

    @Test
    void detailedHtmlIncludesEscapedStackTraceInDev() {
        Request request = htmlRequest();
        Response response = new DefaultExceptionHandler(true)
                .handle(new IllegalStateException("<boom>"), request);
        String body = new String(response.body(), StandardCharsets.UTF_8);

        assertEquals(500, response.status());
        assertTrue(body.contains("IllegalStateException"));
        assertTrue(body.contains("&lt;boom&gt;"));
    }

    @Test
    void detailedJsonIncludesExceptionDetailsInDev() {
        Request request = new Request(
                HttpMethod.GET, "/boom",
                Map.of("Accept", List.of("application/json")),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");
        Response response = new DefaultExceptionHandler(true)
                .handle(new IllegalStateException("boom"), request);
        String body = new String(response.body(), StandardCharsets.UTF_8);

        assertEquals(500, response.status());
        assertTrue(body.contains("\"type\":\"java.lang.IllegalStateException\""));
        assertTrue(body.contains("\"message\":\"boom\""));
    }

    private static Request htmlRequest() {
        return new Request(
                HttpMethod.GET, "/boom",
                Map.of("Accept", List.of("text/html")),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }
}

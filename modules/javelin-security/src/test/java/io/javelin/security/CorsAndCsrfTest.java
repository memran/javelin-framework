package io.javelin.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CorsAndCsrfTest {
    @Test
    void corsPreflightReturnsAllowedHeaders() throws Exception {
        CorsConfig config = new CorsConfig(List.of("https://app.example.com"),
                List.of("GET", "POST"), List.of("Content-Type"), false, 600);
        Request request = new Request(HttpMethod.OPTIONS, "/users", Map.of(
                "Origin", List.of("https://app.example.com"),
                "Access-Control-Request-Method", List.of("POST")),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = new CorsMiddleware(config).handle(request, ignored -> Response.text("unexpected"));

        assertEquals(204, response.status());
        assertEquals("https://app.example.com", response.headers().get("Access-Control-Allow-Origin"));
        assertEquals("GET, POST", response.headers().get("Access-Control-Allow-Methods"));
        assertEquals("600", response.headers().get("Access-Control-Max-Age"));
    }

    @Test
    void corsRejectsUnknownOriginPreflight() throws Exception {
        CorsConfig config = new CorsConfig(List.of("https://app.example.com"),
                List.of("GET"), List.of("Content-Type"), false, 600);
        Request request = new Request(HttpMethod.OPTIONS, "/users", Map.of(
                "Origin", List.of("https://evil.example.com"),
                "Access-Control-Request-Method", List.of("GET")),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");

        Response response = new CorsMiddleware(config).handle(request, ignored -> Response.text("unexpected"));

        assertEquals(403, response.status());
    }

    @Test
    void csrfIssuesTokenOnSafeMethodsAndRejectsMissingTokenOnUnsafeMethods() throws Exception {
        StubSession session = new StubSession("s1");
        Request get = request(HttpMethod.GET, Map.of()).withAttribute(Session.ATTRIBUTE, session);
        Response getResponse = new CsrfMiddleware().handle(get, ignored -> Response.text("ok"));

        String token = getResponse.headers().get(CsrfMiddleware.HEADER);
        assertTrue(token != null && !token.isBlank());

        Request post = request(HttpMethod.POST, Map.of()).withAttribute(Session.ATTRIBUTE, session);
        Response rejected = new CsrfMiddleware().handle(post, ignored -> Response.text("ok"));
        assertEquals(403, rejected.status());

        Request valid = request(HttpMethod.POST, Map.of(CsrfMiddleware.HEADER, List.of(token)))
                .withAttribute(Session.ATTRIBUTE, session);
        Response accepted = new CsrfMiddleware().handle(valid, ignored -> Response.text("ok"));
        assertEquals(200, accepted.status());
    }

    private static Request request(HttpMethod method, Map<String, List<String>> headers) {
        return new Request(method, "/users", headers, Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static final class StubSession implements Session {
        private final String id;
        private final Map<String, Object> values = new java.util.HashMap<>();

        private StubSession(String id) {
            this.id = id;
        }

        @Override public String id() { return id; }
        @Override public Object get(String key) { return values.get(key); }
        @Override public void put(String key, Object value) { values.put(key, value); }
        @Override public void remove(String key) { values.remove(key); }
        @Override public Map<String, Object> all() { return Map.copyOf(values); }
        @Override public void clear() { values.clear(); }
        @Override public void save() { }
        @Override public void invalidate() { values.clear(); }
    }
}

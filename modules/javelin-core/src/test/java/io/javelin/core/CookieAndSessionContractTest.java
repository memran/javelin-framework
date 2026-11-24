package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CookieAndSessionContractTest {
    @Test
    void cookieHeaderValueIncludesSecureAttributes() {
        String header = Cookie.session("sid", "abc123").headerValue();
        assertTrue(header.startsWith("sid=abc123"), header);
        assertTrue(header.contains("Path=/"));
        assertTrue(header.contains("HttpOnly"));
        assertTrue(header.contains("Secure"));
        assertTrue(header.contains("SameSite=Lax"));
        assertTrue(Cookie.expired("sid").headerValue().contains("Max-Age=0"));
    }

    @Test
    void requestParsesCookieHeaderAndExposesSessionAttribute() {
        Request request = new Request(HttpMethod.GET, "/", Map.of("Cookie", List.of("sid=abc; theme=dark")),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");
        assertEquals("abc", request.cookie("sid").orElseThrow());
        assertEquals("dark", request.cookie("theme").orElseThrow());
        assertTrue(request.cookie("missing").isEmpty());
        assertTrue(request.session().isEmpty());

        Session session = new StubSession();
        assertEquals(session, request.withAttribute(Session.ATTRIBUTE, session).session().orElseThrow());
    }

    @Test
    void responseAddsSetCookieHeader() {
        Response response = Response.text("ok").withCookie(Cookie.session("sid", "abc"));
        assertTrue(response.headers().get("Set-Cookie").contains("sid=abc"));
        assertTrue(response.withoutCookie("sid").headers().get("Set-Cookie").contains("Max-Age=0"));
    }

    private static final class StubSession implements Session {
        @Override public String id() { return "s1"; }
        @Override public Object get(String key) { return null; }
        @Override public void put(String key, Object value) { }
        @Override public void remove(String key) { }
        @Override public Map<String, Object> all() { return Map.of(); }
        @Override public void clear() { }
        @Override public void save() { }
        @Override public void invalidate() { }
    }
}

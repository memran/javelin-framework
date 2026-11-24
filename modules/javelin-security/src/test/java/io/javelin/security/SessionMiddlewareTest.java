package io.javelin.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import io.javelin.core.SessionStore;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SessionMiddlewareTest {
    @Test
    void attachesSessionAndReturnsCookie() throws Exception {
        InMemorySessionStore store = new InMemorySessionStore(60);
        SessionMiddleware middleware = new SessionMiddleware(store,
                new SessionConfig("javelin_session", 60, true, true, "Lax"));

        Response response = middleware.handle(request(null), attached -> {
            attached.session().orElseThrow().put("user", "alice");
            return Response.text("ok");
        });

        String cookie = response.headers().get("Set-Cookie");
        assertTrue(cookie.contains("javelin_session="), cookie);
        String id = cookie.substring("javelin_session=".length(), cookie.indexOf(';'));

        Session restored = middlewareSession(middleware, id);
        assertEquals("alice", restored.get("user"));
    }

    @Test
    void invalidationExpiresTheCookie() throws Exception {
        SessionStore store = new InMemorySessionStore(60);
        SessionMiddleware middleware = new SessionMiddleware(store,
                new SessionConfig("javelin_session", 60, true, true, "Lax"));

        Response response = middleware.handle(request(null), attached -> {
            attached.session().orElseThrow().invalidate();
            return Response.redirect("/login");
        });

        assertTrue(response.headers().get("Set-Cookie").contains("Max-Age=0"));
    }

    private Session middlewareSession(SessionMiddleware middleware, String id) throws Exception {
        final Session[] found = new Session[1];
        middleware.handle(request(id), attached -> {
            found[0] = attached.session().orElseThrow();
            return Response.text("ok");
        });
        return found[0];
    }

    private static Request request(String sessionId) {
        Map<String, List<String>> headers = sessionId == null
                ? Map.of()
                : Map.of("Cookie", List.of("javelin_session=" + sessionId));
        return new Request(HttpMethod.GET, "/", headers, Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }
}

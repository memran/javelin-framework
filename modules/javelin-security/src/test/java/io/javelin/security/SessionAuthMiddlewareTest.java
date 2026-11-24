package io.javelin.security;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;
import io.javelin.core.security.RequiresAuthenticationMiddleware;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class SessionAuthMiddlewareTest {
    @Test
    void sessionPrincipalSatisfiesAuthorizationGuard() throws Exception {
        Session session = new StubSession();
        session.put("javelin.user", new Authenticated("alice", Set.of("users.read")));
        Request request = new Request(HttpMethod.GET, "/", Map.of(), Map.of(), Map.of(),
                new byte[0], "127.0.0.1").withAttribute(Session.ATTRIBUTE, session);

        Response response = new SessionAuthMiddleware(SessionPrincipalResolver.defaultResolver())
                .handle(request, authenticated -> new RequiresAuthenticationMiddleware()
                        .handle(authenticated, ignored -> Response.text("ok")));

        assertEquals(200, response.status());
        assertEquals("alice", ((Authenticated) session.get("javelin.user")).subject());
    }

    @Test
    void unresolvedSessionIsNotAuthenticated() throws Exception {
        Request request = new Request(HttpMethod.GET, "/", Map.of(), Map.of(), Map.of(),
                new byte[0], "127.0.0.1").withAttribute(Session.ATTRIBUTE, new StubSession());

        Response response = new SessionAuthMiddleware(SessionPrincipalResolver.defaultResolver())
                .handle(request, authenticated -> new RequiresAuthenticationMiddleware()
                        .handle(authenticated, ignored -> Response.text("ok")));

        assertEquals(401, response.status());
    }

    private static final class StubSession implements Session {
        private final Map<String, Object> values = new java.util.HashMap<>();

        @Override public String id() { return "s1"; }
        @Override public Object get(String key) { return values.get(key); }
        @Override public void put(String key, Object value) { values.put(key, value); }
        @Override public void remove(String key) { values.remove(key); }
        @Override public Map<String, Object> all() { return Map.copyOf(values); }
        @Override public void clear() { values.clear(); }
        @Override public void save() { }
        @Override public void invalidate() { values.clear(); }
    }
}

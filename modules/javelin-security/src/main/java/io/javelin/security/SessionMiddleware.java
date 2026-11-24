package io.javelin.security;

import io.javelin.core.Cookie;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import io.javelin.core.SessionStore;

/** Attaches a session to the request and returns the session cookie on the response. */
public final class SessionMiddleware implements Middleware {
    private final SessionStore store;
    private final SessionConfig config;

    public SessionMiddleware(SessionStore store, SessionConfig config) {
        this.store = store;
        this.config = config;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        Session session = request.cookie(config.cookieName())
                .flatMap(store::find)
                .orElseGet(store::create);
        Request attached = request.withAttribute(Session.ATTRIBUTE, session);
        Response response = next.handle(attached);
        if (!session.isValid()) {
            return response.withCookie(Cookie.expired(config.cookieName()));
        }
        session.save();
        return response.withCookie(new Cookie(
                config.cookieName(), session.id(), config.ttlSeconds(), "/", null,
                config.secure(), config.httpOnly(), config.sameSite()));
    }
}

package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Opt-in provider for session-based authentication. Register after SessionServiceProvider. */
public final class SessionAuthServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(SessionPrincipalResolver.class, SessionPrincipalResolver::defaultResolver);
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new SessionAuthMiddleware(app.make(SessionPrincipalResolver.class)));
    }
}

package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.SessionStore;

/** Opt-in provider for cookie-backed sessions. Register explicitly in config.providers. */
public final class SessionServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(SessionConfig.class, () -> SessionConfig.from(app.config()));
        app.singleton(SessionStore.class,
                () -> new InMemorySessionStore(app.make(SessionConfig.class).ttlSeconds()));
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new SessionMiddleware(
                app.make(SessionStore.class), app.make(SessionConfig.class)));
    }
}

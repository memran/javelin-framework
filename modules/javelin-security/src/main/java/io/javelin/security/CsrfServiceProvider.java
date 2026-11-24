package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Opt-in CSRF protection. Register after SessionServiceProvider. */
public final class CsrfServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        // Middleware is installed in boot() so later providers can register first.
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new CsrfMiddleware());
    }
}

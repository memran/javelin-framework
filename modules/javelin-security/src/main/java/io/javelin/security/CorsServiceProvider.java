package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Opt-in CORS provider. With no configured origins the middleware is a no-op. */
public final class CorsServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(CorsConfig.class, () -> CorsConfig.from(app.config()));
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new CorsMiddleware(app.make(CorsConfig.class)));
    }
}

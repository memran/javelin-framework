package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.security.BearerTokenResolver;

/** Binds a JWT bearer resolver. Use it with BearerAuthMiddleware on protected routes. */
public final class JwtAuthServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(BearerTokenResolver.class, () -> {
            String secret = app.config().getString("security.jwt.secret")
                    .orElseGet(() -> app.env().get("APP_KEY", ""));
            return new JwtTokenResolver(
                    secret,
                    app.config().getString("security.jwt.issuer", ""),
                    app.config().getString("security.jwt.audience", ""));
        });
    }
}

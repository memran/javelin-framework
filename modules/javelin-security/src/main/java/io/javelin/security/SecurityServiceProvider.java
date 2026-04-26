package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class SecurityServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(PasswordHasher.class, PasswordHasher::new);
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new SecureHeadersMiddleware());
        app.router().middleware(new RequestSizeLimitMiddleware(app.config().getInt("security.max_request_bytes", 1_048_576)));
    }
}

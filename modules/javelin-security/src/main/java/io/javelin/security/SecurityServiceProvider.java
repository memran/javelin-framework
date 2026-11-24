package io.javelin.security;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class SecurityServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(PasswordHashStrategy.class, () -> {
            String algorithm = app.config().getString("security.password.hash", "pbkdf2");
            return switch (algorithm.toLowerCase(java.util.Locale.ROOT)) {
                case "bcrypt" -> new BcryptStrategy(app.config().getInt("security.password.bcrypt_cost", 12));
                case "pbkdf2" -> new Pbdkdf2Strategy();
                default -> throw new IllegalArgumentException("Unknown password hash strategy: " + algorithm);
            };
        });
        app.singleton(PasswordHasher.class, () -> new PasswordHasher(app.make(PasswordHashStrategy.class)));
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new SecureHeadersMiddleware(app.config()));
        app.router().middleware(new RequestSizeLimitMiddleware(app.config().getInt("security.max_request_bytes", 1_048_576)));
    }
}

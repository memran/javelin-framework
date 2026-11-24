package io.javelin.config;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

import java.nio.file.Path;
import java.util.List;

/** Binds a secret provider that reads environment variables first, then secret files. */
public final class SecretsServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(SecretProvider.class, () -> new CompositeSecretProvider(List.of(
                new EnvSecretProvider(app.env()),
                new FileSecretProvider(Path.of(app.config().getString("secrets.dir", "secrets"))))));
    }
}

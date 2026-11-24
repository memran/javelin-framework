package io.javelin.config;

import io.javelin.core.Env;

import java.util.Objects;
import java.util.Optional;

/** Reads secrets from the environment (including .env). */
public final class EnvSecretProvider implements SecretProvider {
    private final Env env;

    public EnvSecretProvider(Env env) {
        this.env = Objects.requireNonNull(env, "env");
    }

    @Override
    public Optional<String> get(String name) {
        return env.get(name).filter(value -> !value.isBlank());
    }
}

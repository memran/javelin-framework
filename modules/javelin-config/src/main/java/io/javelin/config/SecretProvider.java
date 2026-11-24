package io.javelin.config;

import java.util.Optional;

/** Explicit secret lookup contract. */
public interface SecretProvider {
    Optional<String> get(String name);
}

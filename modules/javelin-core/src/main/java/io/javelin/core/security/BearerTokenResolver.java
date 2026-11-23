package io.javelin.core.security;

import java.util.Optional;

/** Resolves a Bearer token to a principal. Returning empty rejects the token. */
@FunctionalInterface
public interface BearerTokenResolver {
    Optional<Authenticated> resolve(String token);
}

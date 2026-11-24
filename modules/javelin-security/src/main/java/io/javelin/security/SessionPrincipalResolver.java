package io.javelin.security;

import io.javelin.core.Session;
import io.javelin.core.security.Authenticated;

import java.util.Optional;
import java.util.Set;

/** Resolves an authenticated principal from session state. */
@FunctionalInterface
public interface SessionPrincipalResolver {
    Optional<Authenticated> resolve(Session session);

    /** Default resolver: reads the "javelin.user" session attribute. */
    static SessionPrincipalResolver defaultResolver() {
        return session -> {
            Object value = session.get("javelin.user");
            if (value instanceof Authenticated principal) {
                return Optional.of(principal);
            }
            if (value instanceof String subject && !subject.isBlank()) {
                return Optional.of(new Authenticated(subject, Set.of()));
            }
            return Optional.empty();
        };
    }
}

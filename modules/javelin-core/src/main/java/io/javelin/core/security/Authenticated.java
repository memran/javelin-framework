package io.javelin.core.security;

import java.util.Objects;
import java.util.Set;

/** Minimal principal: a subject name and the permissions granted to it. */
public record Authenticated(String subject, Set<String> permissions) {
    public Authenticated {
        Objects.requireNonNull(subject, "subject");
        permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
    }

    public boolean hasPermission(String permission) {
        return permission != null && permissions.contains(permission);
    }
}

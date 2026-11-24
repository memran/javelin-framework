package io.javelin.security;

import java.util.Objects;

/** Password hashing facade. Defaults to PBKDF2; BCrypt is available via strategy. */
public final class PasswordHasher {
    private static final PasswordHashStrategy DEFAULT = new Pbdkdf2Strategy();

    private final PasswordHashStrategy strategy;

    public PasswordHasher() {
        this(DEFAULT);
    }

    public PasswordHasher(PasswordHashStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public String hash(String password) {
        return strategy.hash(password);
    }

    /** Verifies hashes produced by the active strategy, plus legacy PBKDF2/BCrypt values. */
    public boolean verify(String password, String hash) {
        if (hash == null || hash.isBlank()) {
            return false;
        }
        if (hash.startsWith("$2")) {
            return new BcryptStrategy().verify(password, hash);
        }
        if (hash.matches("^\\d+:.+:.+$")) {
            return DEFAULT.verify(password, hash);
        }
        return strategy.verify(password, hash);
    }
}

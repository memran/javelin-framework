package io.javelin.security;

import org.mindrot.jbcrypt.BCrypt;

/** BCrypt strategy with a configurable cost factor (4-31, default 12). */
public final class BcryptStrategy implements PasswordHashStrategy {
    private final int rounds;

    public BcryptStrategy() {
        this(12);
    }

    public BcryptStrategy(int rounds) {
        if (rounds < 4 || rounds > 31) {
            throw new IllegalArgumentException("BCrypt cost must be between 4 and 31");
        }
        this.rounds = rounds;
    }

    @Override
    public String id() {
        return "bcrypt";
    }

    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(rounds));
    }

    @Override
    public boolean verify(String password, String hash) {
        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}

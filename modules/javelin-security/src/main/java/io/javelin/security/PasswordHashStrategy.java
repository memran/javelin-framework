package io.javelin.security;

/** Pluggable password hashing strategy. */
public interface PasswordHashStrategy {
    String id();

    String hash(String password);

    boolean verify(String password, String hash);
}

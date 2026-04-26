package io.javelin.security;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordHasher {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ITERATIONS = 210_000;
    private static final int KEY_BITS = 256;

    public String hash(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] digest = digest(salt, password);
        return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(digest);
    }

    public boolean verify(String password, String hash) {
        String[] parts = hash.split(":", 3);
        if (parts.length != 3) {
            return false;
        }
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] expected = Base64.getDecoder().decode(parts[2]);
        return MessageDigest.isEqual(expected, digest(Integer.parseInt(parts[0]), salt, password));
    }

    private byte[] digest(byte[] salt, String password) {
        return digest(ITERATIONS, salt, password);
    }

    private byte[] digest(int iterations, byte[] salt, String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, KEY_BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to hash password", exception);
        }
    }
}

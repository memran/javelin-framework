package io.javelin.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PasswordHashStrategyTest {
    @Test
    void bcryptHashesAndVerifies() {
        BcryptStrategy strategy = new BcryptStrategy(4);
        String hash = strategy.hash("correct horse battery staple");

        assertTrue(hash.startsWith("$2"));
        assertTrue(strategy.verify("correct horse battery staple", hash));
        assertFalse(strategy.verify("wrong", hash));
    }

    @Test
    void facadeSupportsBothStrategiesAndLegacyHashes() {
        Pbdkdf2Strategy pbkdf2 = new Pbdkdf2Strategy();
        String legacy = pbkdf2.hash("secret");
        PasswordHasher bcrypt = new PasswordHasher(new BcryptStrategy(4));
        String modern = bcrypt.hash("secret");

        assertTrue(bcrypt.verify("secret", legacy));
        assertTrue(bcrypt.verify("secret", modern));
        assertNotEquals(legacy, modern);
    }

    @Test
    void rejectsInvalidBcryptCost() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new BcryptStrategy(2));
    }
}

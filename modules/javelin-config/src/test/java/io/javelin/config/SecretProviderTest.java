package io.javelin.config;

import io.javelin.core.Env;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SecretProviderTest {
    @Test
    void readsSecretsFromFiles(@TempDir Path dir) throws Exception {
        Files.writeString(dir.resolve("DB_PASSWORD"), "file-secret\n");
        assertTrue(new FileSecretProvider(dir).get("DB_PASSWORD").orElseThrow().equals("file-secret"));
        assertTrue(new FileSecretProvider(dir).get("MISSING").isEmpty());
        assertTrue(new FileSecretProvider(dir).get("../outside").isEmpty());
    }

    @Test
    void environmentTakesPrecedence(@TempDir Path dir) throws Exception {
        Files.writeString(dir.resolve("APP_KEY"), "file-key");
        Env env = key -> key.equals("APP_KEY") ? Optional.of("env-key") : Optional.empty();
        CompositeSecretProvider provider = new CompositeSecretProvider(List.of(
                new EnvSecretProvider(env), new FileSecretProvider(dir)));

        assertEquals("env-key", provider.get("APP_KEY").orElseThrow());
    }
}

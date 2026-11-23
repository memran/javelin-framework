package io.javelin.config;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class DotenvEnvTest {
    @Test
    void parsesKeysEvenWhenFileStartsWithABom() throws Exception {
        Path file = Files.createTempFile("javelin-dotenv", ".env");
        Files.write(file, ("\uFEFFAPP_ENV=prod\n").getBytes(StandardCharsets.UTF_8));

        DotenvEnv env = DotenvEnv.load(file);

        assertEquals("prod", env.get("APP_ENV").orElseThrow());
    }
}

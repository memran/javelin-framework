package io.javelin.http.jdk;

import io.javelin.core.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StaticAssetResolverTest {
    @Test
    void servesFilesFromTheConfiguredStaticRoot() throws Exception {
        Path root = Files.createTempDirectory("javelin-static");
        Files.writeString(root.resolve("index.html"), "<h1>Hello</h1>", StandardCharsets.UTF_8);
        Files.createDirectories(root.resolve("css"));
        Files.writeString(root.resolve("css/app.css"), "body { color: #111; }", StandardCharsets.UTF_8);

        StaticAssetResolver resolver = new StaticAssetResolver(root);

        Response index = resolver.resolve("GET", "/").orElseThrow();
        Response stylesheet = resolver.resolve("GET", "/css/app.css").orElseThrow();
        Response head = resolver.resolve("HEAD", "/css/app.css").orElseThrow();

        assertEquals(200, index.status());
        assertEquals("<h1>Hello</h1>", new String(index.body(), StandardCharsets.UTF_8));
        assertEquals("text/html; charset=utf-8", index.headers().get("Content-Type"));
        assertEquals("text/css; charset=utf-8", stylesheet.headers().get("Content-Type"));
        assertEquals("body { color: #111; }", new String(stylesheet.body(), StandardCharsets.UTF_8));
        assertEquals(0, head.body().length);
        assertTrue(resolver.resolve("GET", "/../secret.txt").isEmpty());
        assertFalse(resolver.resolve("POST", "/css/app.css").isPresent());
    }
}

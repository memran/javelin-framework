package io.javelin.view.pebble;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class PebbleViewRendererTest {
    @Test
    void reloadsFilesystemTemplatesWithoutRebuild() throws Exception {
        Path root = Files.createTempDirectory("javelin-views");
        Path views = root.resolve("resources/views");
        Files.createDirectories(views);
        Path template = views.resolve("welcome.peb");
        Files.writeString(template, "Hello {{ name }}", StandardCharsets.UTF_8);

        PebbleViewRenderer renderer = new PebbleViewRenderer(root, "resources/views");

        String first = new String(renderer.render("welcome", Map.of("name", "Javelin")).body(), StandardCharsets.UTF_8);
        Files.writeString(template, "Updated {{ name }}", StandardCharsets.UTF_8);
        String second = new String(renderer.render("welcome", Map.of("name", "Javelin")).body(), StandardCharsets.UTF_8);

        assertTrue(first.contains("Hello Javelin"));
        assertTrue(second.contains("Updated Javelin"));
    }
}

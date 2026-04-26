package io.javelin.view.pebble;

import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.extension.Function;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

    @Test
    void rendersRegisteredFiltersAndFunctions() throws Exception {
        Path root = Files.createTempDirectory("javelin-views");
        Path views = root.resolve("resources/views");
        Files.createDirectories(views);
        Files.writeString(views.resolve("welcome.peb"), "{{ name|shout }} {{ framework_name() }}", StandardCharsets.UTF_8);
        PebbleViewExtensions extensions = new PebbleViewExtensions()
                .filter("shout", shoutFilter())
                .function("framework_name", frameworkNameFunction());

        PebbleViewRenderer renderer = new PebbleViewRenderer(
                root,
                "resources/views",
                PebbleViewRenderer.Options.defaults(),
                extensions.extensions()
        );

        String body = new String(renderer.render("welcome", Map.of("name", "hello")).body(), StandardCharsets.UTF_8);

        assertTrue(body.contains("HELLO Javelin"));
    }

    private Filter shoutFilter() {
        return new Filter() {
            @Override
            public Object apply(Object input, Map<String, Object> args, io.pebbletemplates.pebble.template.PebbleTemplate self,
                                io.pebbletemplates.pebble.template.EvaluationContext context, int lineNumber) {
                return input == null ? "" : input.toString().toUpperCase();
            }

            @Override
            public List<String> getArgumentNames() {
                return List.of();
            }
        };
    }

    private Function frameworkNameFunction() {
        return new Function() {
            @Override
            public Object execute(Map<String, Object> args, io.pebbletemplates.pebble.template.PebbleTemplate self,
                                  io.pebbletemplates.pebble.template.EvaluationContext context, int lineNumber) {
                return "Javelin";
            }

            @Override
            public List<String> getArgumentNames() {
                return List.of();
            }
        };
    }
}

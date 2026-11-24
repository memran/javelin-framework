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

    @Test
    void rendersLayoutsIncludesAndBlocks() throws Exception {
        Path root = Files.createTempDirectory("javelin-view-layouts");
        Path views = root.resolve("resources/views");
        Files.createDirectories(views);
        Files.writeString(views.resolve("layout.peb"),
                "<html>{% block content %}{% endblock %}{% include \"footer\" %}</html>");
        Files.writeString(views.resolve("footer.peb"), "<footer>end</footer>");
        Files.writeString(views.resolve("page.peb"),
                "{% extends \"layout\" %}{% block content %}Hello {{ name }}{% endblock %}");

        PebbleViewRenderer renderer = new PebbleViewRenderer(root, "resources/views");

        String body = new String(renderer.render("page", Map.of("name", "Javelin")).body(), StandardCharsets.UTF_8);
        assertTrue(body.contains("<html>"), body);
        assertTrue(body.contains("Hello Javelin"), body);
        assertTrue(body.contains("<footer>end</footer>"), body);
    }

    @Test
    void rendersImportedMacrosAsReusableComponents() throws Exception {
        Path root = Files.createTempDirectory("javelin-view-macros");
        Path views = root.resolve("resources/views");
        Files.createDirectories(views);
        Files.writeString(views.resolve("alert.peb"),
                "{% macro alert(message) %}<div>{{ message }}</div>{% endmacro %}");
        Files.writeString(views.resolve("page.peb"),
                "{% import \"alert\" as ui %}{{ ui.alert(\"Saved\") }}");
        PebbleViewRenderer renderer = new PebbleViewRenderer(root, "resources/views");

        String body = new String(renderer.render("page", Map.of()).body(), StandardCharsets.UTF_8);
        assertTrue(body.contains("<div>Saved</div>"), body);
    }

    @Test
    void autoEscapesVariablesByDefault() throws Exception {
        Path root = Files.createTempDirectory("javelin-view-escaping");
        Path views = root.resolve("resources/views");
        Files.createDirectories(views);
        Files.writeString(views.resolve("escaped.peb"), "{{ value }}");
        Files.writeString(views.resolve("raw.peb"), "{{ value|raw }}");
        PebbleViewRenderer renderer = new PebbleViewRenderer(root, "resources/views");

        String escaped = new String(renderer.render("escaped", Map.of("value", "<b>x</b>")).body(), StandardCharsets.UTF_8);
        String raw = new String(renderer.render("raw", Map.of("value", "<b>x</b>")).body(), StandardCharsets.UTF_8);

        assertTrue(escaped.contains("&lt;b&gt;"), escaped);
        assertTrue(!escaped.contains("<b>"), escaped);
        assertTrue(raw.contains("<b>x</b>"), raw);
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

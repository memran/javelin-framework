package io.javelin.view.pebble;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.loader.FileLoader;
import io.javelin.core.HtmlResponse;
import io.javelin.core.ViewRenderer;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class PebbleViewRenderer implements ViewRenderer {
    private final PebbleEngine engine;
    private final Map<String, Object> globals = new HashMap<>();

    public PebbleViewRenderer(String prefix) {
        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(".peb");
        this.engine = new PebbleEngine.Builder().loader(loader).autoEscaping(true).build();
    }

    public PebbleViewRenderer(Path root, String prefix) {
        Path views = root.resolve(prefix).toAbsolutePath().normalize();
        if (Files.isDirectory(views)) {
            FileLoader loader = new FileLoader();
            loader.setPrefix(views.toString());
            loader.setSuffix(".peb");
            this.engine = new PebbleEngine.Builder()
                    .loader(loader)
                    .cacheActive(false)
                    .autoEscaping(true)
                    .build();
            return;
        }

        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(".peb");
        this.engine = new PebbleEngine.Builder().loader(loader).autoEscaping(true).build();
    }

    public void share(String key, Object value) {
        globals.put(key, value);
    }

    @Override
    public HtmlResponse render(String template, Map<String, Object> data) {
        try {
            Map<String, Object> context = new HashMap<>(globals);
            context.putAll(data);
            StringWriter writer = new StringWriter();
            engine.getTemplate(template).evaluate(writer, context);
            return new HtmlResponse(writer.toString());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to render view " + template, exception);
        }
    }
}

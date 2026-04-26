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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class PebbleViewRenderer implements ViewRenderer {
    private final PebbleEngine engine;
    private final Map<String, Object> globals = new HashMap<>();

    public PebbleViewRenderer(String prefix) {
        this(prefix, Options.defaults());
    }

    public PebbleViewRenderer(String prefix, Options options) {
        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(options.suffix());
        this.engine = builder(loader, options).build();
    }

    public PebbleViewRenderer(Path root, String prefix) {
        this(root, prefix, Options.defaults());
    }

    public PebbleViewRenderer(Path root, String prefix, Options options) {
        Path views = root.resolve(prefix).toAbsolutePath().normalize();
        if (Files.isDirectory(views)) {
            FileLoader loader = new FileLoader();
            loader.setPrefix(views.toString());
            loader.setSuffix(options.suffix());
            this.engine = builder(loader, options).build();
            return;
        }

        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(options.suffix());
        this.engine = builder(loader, options).build();
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

    private static PebbleEngine.Builder builder(io.pebbletemplates.pebble.loader.Loader<?> loader, Options options) {
        PebbleEngine.Builder builder = new PebbleEngine.Builder()
                .loader(loader)
                .cacheActive(options.cache())
                .autoEscaping(options.autoEscaping())
                .strictVariables(options.strictVariables())
                .newLineTrimming(options.newLineTrimming())
                .allowOverrideCoreOperators(options.allowOverrideCoreOperators())
                .literalDecimalTreatedAsInteger(options.literalDecimalTreatedAsInteger())
                .literalNumbersAsBigDecimals(options.literalNumbersAsBigDecimals())
                .greedyMatchMethod(options.greedyMatchMethod());

        options.defaultLocale().ifPresent(builder::defaultLocale);
        options.defaultEscapingStrategy().ifPresent(builder::defaultEscapingStrategy);
        options.maxRenderedSize().ifPresent(builder::maxRenderedSize);
        return builder;
    }

    public record Options(
            String suffix,
            boolean cache,
            boolean autoEscaping,
            boolean strictVariables,
            boolean newLineTrimming,
            Optional<Locale> defaultLocale,
            Optional<Integer> maxRenderedSize,
            boolean allowOverrideCoreOperators,
            Optional<String> defaultEscapingStrategy,
            boolean literalDecimalTreatedAsInteger,
            boolean literalNumbersAsBigDecimals,
            boolean greedyMatchMethod
    ) {
        public static Options defaults() {
            return new Options(
                    ".peb",
                    false,
                    true,
                    false,
                    false,
                    Optional.empty(),
                    Optional.empty(),
                    false,
                    Optional.empty(),
                    false,
                    false,
                    false
            );
        }
    }
}

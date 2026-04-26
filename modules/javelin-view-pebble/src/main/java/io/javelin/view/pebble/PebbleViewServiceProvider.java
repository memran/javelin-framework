package io.javelin.view.pebble;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.View;
import io.javelin.core.ViewRenderer;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

public final class PebbleViewServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(ViewRenderer.class, () -> {
            String prefix = app.config().getString("view.prefix", "resources/views");
            PebbleViewRenderer.Options options = options(app);
            if (app.has(Path.class)) {
                return new PebbleViewRenderer(app.make(Path.class), prefix, options);
            }
            return new PebbleViewRenderer(prefix, options);
        });
    }

    @Override
    public void boot(Application app) {
        View.use(app.make(ViewRenderer.class));
    }

    private PebbleViewRenderer.Options options(Application app) {
        return new PebbleViewRenderer.Options(
                app.config().getString("view.suffix", ".peb"),
                app.config().getBoolean("view.pebble.cache").orElse(false),
                app.config().getBoolean("view.pebble.auto_escaping").orElse(true),
                app.config().getBoolean("view.pebble.strict_variables").orElse(false),
                app.config().getBoolean("view.pebble.new_line_trimming").orElse(false),
                app.config().getString("view.pebble.default_locale").map(Locale::forLanguageTag),
                positiveInt(app.config().getInt("view.pebble.max_rendered_size")),
                app.config().getBoolean("view.pebble.allow_override_core_operators").orElse(false),
                app.config().getString("view.pebble.default_escaping_strategy"),
                app.config().getBoolean("view.pebble.literal_decimal_treated_as_integer").orElse(false),
                app.config().getBoolean("view.pebble.literal_numbers_as_big_decimals").orElse(false),
                app.config().getBoolean("view.pebble.greedy_match_method").orElse(false)
        );
    }

    private Optional<Integer> positiveInt(Optional<Integer> value) {
        return value.filter(number -> number > 0);
    }
}

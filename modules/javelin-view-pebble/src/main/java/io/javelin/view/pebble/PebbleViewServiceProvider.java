package io.javelin.view.pebble;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.View;
import io.javelin.core.ViewRenderer;

import java.nio.file.Path;

public final class PebbleViewServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(ViewRenderer.class, () -> {
            String prefix = app.config().getString("view.prefix", "resources/views");
            if (app.has(Path.class)) {
                return new PebbleViewRenderer(app.make(Path.class), prefix);
            }
            return new PebbleViewRenderer(prefix);
        });
    }

    @Override
    public void boot(Application app) {
        View.use(app.make(ViewRenderer.class));
    }
}

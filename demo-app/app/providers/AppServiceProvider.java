package app.providers;

import app.views.JavelinViewExtension;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.view.pebble.PebbleViewExtensions;
import routes.web;

public final class AppServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.make(PebbleViewExtensions.class)
                .extension(new JavelinViewExtension())
                .global("javelinRuntime", "JDK 25");
    }

    @Override
    public void boot(Application app) {
        web.register(app);
    }
}

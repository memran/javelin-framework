package io.javelin.console;

import io.javelin.core.Application;
import io.javelin.core.ConsoleKernel;
import io.javelin.core.ServiceProvider;

public final class ConsoleServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(ConsoleKernel.class, () -> new JavelinConsole(app));
    }
}

package io.javelin.console;

import io.javelin.core.Application;
import picocli.CommandLine;

@CommandLine.Command(name = "serve", description = "Start the HTTP server")
public final class ServeCommand implements Runnable {
    private final Application app;

    public ServeCommand(Application app) {
        this.app = app;
    }

    @Override
    public void run() {
        JavelinConsole.start(app);
    }
}

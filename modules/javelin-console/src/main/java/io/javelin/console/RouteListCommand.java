package io.javelin.console;

import io.javelin.core.Application;
import io.javelin.core.Route;
import picocli.CommandLine;

@CommandLine.Command(name = "route:list", description = "List registered routes")
public final class RouteListCommand implements Runnable {
    private final Application app;

    public RouteListCommand(Application app) {
        this.app = app;
    }

    @Override
    public void run() {
        app.boot();
        for (Route route : app.router().routes()) {
            System.out.printf("%-6s %-32s %s%n", route.method(), route.path(), route.name().orElse(""));
        }
    }
}

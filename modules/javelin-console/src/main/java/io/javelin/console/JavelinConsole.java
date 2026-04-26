package io.javelin.console;

import io.javelin.core.Application;
import io.javelin.core.ConsoleKernel;
import io.javelin.core.HttpServerAdapter;

import java.util.concurrent.CountDownLatch;

public final class JavelinConsole implements ConsoleKernel {
    private final Application app;

    public JavelinConsole(Application app) {
        this.app = app;
    }

    @Override
    public int run(String[] args) {
        return new io.javelin.cli.ConsoleKernel(app).run(args);
    }

    public Application app() {
        return app;
    }

    static void start(Application app) {
        app.boot();
        HttpServerAdapter server = app.make(HttpServerAdapter.class);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(1)));
        server.start();
        System.out.printf("Javelin running at http://%s:%d%n",
                app.config().getString("server.host", "127.0.0.1"),
                app.config().getInt("server.port", 8080));
        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}

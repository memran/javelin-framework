package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.core.Application;
import io.javelin.core.HttpServerAdapter;
import picocli.CommandLine;

import java.util.concurrent.CountDownLatch;

@CommandLine.Command(name = "serve", description = "Start the Javelin HTTP server")
public final class ServeCommand extends AbstractCommand {
    @CommandLine.Option(names = "--watch", description = "Show watch-mode hint")
    boolean watch;

    public ServeCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        long started = System.nanoTime();
        Application app = context.requireApplication();
        app.boot();
        HttpServerAdapter server = app.make(HttpServerAdapter.class);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(1)));
        server.start();
        String host = app.config().getString("server.host", "127.0.0.1");
        int actualPort = app.config().getInt("server.port", 8080);
        context.output().success("Server running: http://" + host + ":" + actualPort);
        context.output().info("Routes: " + app.router().routes().size() + " | Startup: " + ((System.nanoTime() - started) / 1_000_000) + "ms");
        if (watch) {
            context.output().info("Watch mode is available through `javelin dev`.");
        }
        awaitShutdown();
    }

    private void awaitShutdown() {
        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}

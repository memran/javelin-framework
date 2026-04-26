package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.core.Application;
import io.javelin.core.Route;
import picocli.CommandLine;

@CommandLine.Command(name = "routes:list", description = "List registered routes")
public final class RoutesListCommand extends AbstractCommand {
    public RoutesListCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Application app = context.requireApplication();
        app.boot();
        context.output().line(String.format("%-8s %-36s %s", "Method", "Path", "Name"));
        for (Route route : app.router().routes()) {
            context.output().line(String.format("%-8s %-36s %s",
                    route.method(), route.path(), route.name().orElse("")));
        }
    }
}

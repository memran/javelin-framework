package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "dev", description = "Run development mode with hot reload guidance")
public final class DevCommand extends AbstractCommand {
    public DevCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().info("Development mode watches source changes and restarts the app.");
        context.output().info("Use `mvn compile exec:java` today; file watching will be wired to the runtime adapter next.");
    }
}

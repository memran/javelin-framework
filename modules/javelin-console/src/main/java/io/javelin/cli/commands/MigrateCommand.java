package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "migrate", description = "Run pending database migrations")
public final class MigrateCommand extends AbstractCommand {
    public MigrateCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().info("No migration runner is configured yet.");
        context.output().info("Migration files live in database/migrations.");
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "migrate:rollback", description = "Rollback the last migration batch")
public final class MigrateRollbackCommand extends AbstractCommand {
    public MigrateRollbackCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().warn("Rollback is waiting on the migration runner implementation.");
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.db.jdbc.MigrationRunner;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "migrate:rollback", description = "Rollback the last migration batch")
public final class MigrateRollbackCommand extends AbstractCommand {
    public MigrateRollbackCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        var app = context.requireApplication();
        if (!app.has(MigrationRunner.class)) {
            context.output().info("No migration runner is configured yet.");
            context.output().info("Install `javelin-db-jdbc` and register the JDBC database service provider.");
            return;
        }
        List<String> rolledBack = app.make(MigrationRunner.class).rollback();
        if (rolledBack.isEmpty()) {
            context.output().info("No migrations to rollback.");
            return;
        }
        context.output().success("Rolled back migrations: " + String.join(", ", rolledBack));
    }
}

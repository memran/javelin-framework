package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.db.jdbc.MigrationRunner;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "migrate", description = "Run pending database migrations")
public final class MigrateCommand extends AbstractCommand {
    public MigrateCommand(CommandContext context) {
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
        List<String> ran = app.make(MigrationRunner.class).migrate();
        if (ran.isEmpty()) {
            context.output().info("No pending migrations.");
            return;
        }
        context.output().success("Applied migrations: " + String.join(", ", ran));
    }
}

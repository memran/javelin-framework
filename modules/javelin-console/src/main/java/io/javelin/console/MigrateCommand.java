package io.javelin.console;

import picocli.CommandLine;

@CommandLine.Command(name = "migrate", description = "Run database migrations")
public final class MigrateCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Migrations are planned for the next phase.");
    }
}

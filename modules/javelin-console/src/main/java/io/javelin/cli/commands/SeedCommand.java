package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "seed", description = "Run database seeders")
public final class SeedCommand extends AbstractCommand {
    public SeedCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().info("No seeders were run. Add seed classes under database/seeders.");
    }
}

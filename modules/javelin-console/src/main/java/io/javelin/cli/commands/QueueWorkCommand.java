package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "queue:work", description = "Start a queue worker")
public final class QueueWorkCommand extends AbstractCommand {
    @CommandLine.Option(names = "--once", description = "Process one job and exit")
    boolean once;

    public QueueWorkCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().info(once ? "Queue worker checked once; no queue backend configured." : "Queue worker ready; no queue backend configured.");
    }
}

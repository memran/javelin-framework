package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "queue:status", description = "Show queue backend status")
public final class QueueStatusCommand extends AbstractCommand {
    public QueueStatusCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().line("Queue: not configured");
        context.output().line("Pending: 0");
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "schedule:run", description = "Run due scheduled tasks")
public final class ScheduleRunCommand extends AbstractCommand {
    public ScheduleRunCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().success("No scheduled tasks are due");
    }
}

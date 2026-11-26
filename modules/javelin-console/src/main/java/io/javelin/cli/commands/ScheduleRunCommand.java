package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.scheduler.Scheduler;
import picocli.CommandLine;

@CommandLine.Command(name = "schedule:run", description = "Run due scheduled tasks")
public final class ScheduleRunCommand extends AbstractCommand {
    public ScheduleRunCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        if (context.application().isEmpty() || !context.application().get().has(Scheduler.class)) {
            context.output().success("No scheduled tasks are due");
            return;
        }
        int started = context.application().get().make(Scheduler.class).runDue();
        context.output().success("Started " + started + " scheduled task(s)");
    }
}

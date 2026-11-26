package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.core.Application;
import io.javelin.queue.JobQueue;
import io.javelin.queue.JobHandlerRegistry;
import picocli.CommandLine;

@CommandLine.Command(name = "queue:status", description = "Show queue backend status")
public final class QueueStatusCommand extends AbstractCommand {
    public QueueStatusCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        if (context.application().isEmpty() || !context.application().get().has(JobQueue.class)) {
            context.output().line("Queue: not configured");
            context.output().line("Pending: 0");
            return;
        }
        Application app = context.application().get();
        JobQueue queue = app.make(JobQueue.class);
        JobHandlerRegistry registry = app.has(JobHandlerRegistry.class)
                ? app.make(JobHandlerRegistry.class)
                : null;
        context.output().line("Queue: " + queue.getClass().getSimpleName());
        context.output().line("Pending: " + queue.pendingCount());
        context.output().line("Handlers: " + (registry == null ? 0 : registry.subjects().size()));
    }
}

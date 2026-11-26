package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.core.Application;
import io.javelin.queue.JobHandlerRegistry;
import io.javelin.queue.JobQueue;
import io.javelin.queue.QueueWorker;
import io.javelin.queue.QueueWorkerConfig;
import picocli.CommandLine;

import java.util.concurrent.CountDownLatch;

@CommandLine.Command(name = "queue:work", description = "Start a queue worker")
public final class QueueWorkCommand extends AbstractCommand {
    @CommandLine.Option(names = "--once", description = "Process one job and exit")
    boolean once;

    public QueueWorkCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        if (context.application().isEmpty() || !context.application().get().has(JobQueue.class)) {
            context.output().info(once
                    ? "Queue worker checked once; no queue backend configured."
                    : "Queue worker ready; no queue backend configured.");
            return;
        }
        Application app = context.application().get();
        JobQueue queue = app.make(JobQueue.class);
        JobHandlerRegistry registry = app.make(JobHandlerRegistry.class);
        QueueWorkerConfig config = app.make(QueueWorkerConfig.class);

        try (QueueWorker worker = new QueueWorker(queue, registry, config)) {
            if (once) {
                int processed = worker.runOnce(1);
                context.output().info("Processed " + processed + " job(s).");
                return;
            }
            Runtime.getRuntime().addShutdownHook(new Thread(worker::close, "javelin-queue-shutdown"));
            worker.start();
            context.output().info("Queue worker started with concurrency " + config.concurrency() + ".");
            try {
                new CountDownLatch(1).await();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

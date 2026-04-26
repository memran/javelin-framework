package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.cli.runtime.ProcessTasks;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "native", description = "Build a GraalVM native image")
public final class NativeCommand extends AbstractCommand {
    public NativeCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        ProcessTasks.run(context, "Native image", List.of("mvn", "-Pnative", "native:compile"));
        context.output().success("Native image complete");
    }
}

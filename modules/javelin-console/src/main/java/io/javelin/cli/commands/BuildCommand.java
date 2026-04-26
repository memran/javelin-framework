package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.cli.runtime.ProcessTasks;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "build", description = "Build the current project")
public final class BuildCommand extends AbstractCommand {
    public BuildCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        ProcessTasks.run(context, "Build", List.of("mvn", "-q", "package"));
        context.output().success("Build complete");
    }
}

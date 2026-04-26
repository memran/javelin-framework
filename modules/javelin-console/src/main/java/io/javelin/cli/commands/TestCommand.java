package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.cli.runtime.ProcessTasks;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "test", description = "Run project tests")
public final class TestCommand extends AbstractCommand {
    public TestCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        ProcessTasks.run(context, "Tests", List.of("mvn", "test"));
        context.output().success("Tests passed");
    }
}

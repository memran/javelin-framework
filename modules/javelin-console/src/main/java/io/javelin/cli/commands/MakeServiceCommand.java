package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:service", description = "Create an application service")
public final class MakeServiceCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeServiceCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        Path path = generate(name, "Service", "app.services", context.workingDirectory().resolve("app"), "service.stub");
        context.output().success("Service created: " + context.workingDirectory().relativize(path));
    }
}

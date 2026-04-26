package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:controller", description = "Create a controller class")
public final class MakeControllerCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0", description = "Controller name")
    String name;
    @CommandLine.Option(names = "--force", description = "Overwrite an existing file")
    boolean overwrite;

    public MakeControllerCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        Path path = generate(name, "Controller", "app.controllers", context.workingDirectory().resolve("app"), "controller.stub");
        context.output().success("Controller created: " + context.workingDirectory().relativize(path));
    }
}

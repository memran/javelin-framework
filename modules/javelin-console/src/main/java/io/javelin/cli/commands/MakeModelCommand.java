package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:model", description = "Create a model class")
public final class MakeModelCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeModelCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        Path path = generate(name, "Model", "app.models", context.workingDirectory().resolve("app"), "model.stub");
        context.output().success("Model created: " + context.workingDirectory().relativize(path));
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "ai:generate", description = "Generate framework artifacts with AI assistance")
public final class AiGenerateModuleCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0", description = "Artifact type, for example: module")
    String type;
    @CommandLine.Parameters(index = "1", description = "Artifact name")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public AiGenerateModuleCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        if (!"module".equalsIgnoreCase(type)) {
            context.output().warn("Only `ai:generate module <name>` is implemented in this MVP.");
            return;
        }
        MakeModuleCommand command = new MakeModuleCommand(context);
        command.name = name;
        command.overwrite = overwrite;
        command.run();
        context.output().info("AI prompt context saved for module: " + name);
    }
}

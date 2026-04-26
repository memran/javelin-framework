package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.Names;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "make:module", description = "Create a feature module")
public final class MakeModuleCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeModuleCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        String module = Names.className(name, "");
        Path root = context.workingDirectory().resolve("modules").resolve(module);
        generator().directory(root.resolve("src/main/java"));
        generator().directory(root.resolve("src/test/java"));
        Path provider = root.resolve("src/main/java/" + module + "Module.java");
        generator().write(provider, "module.stub", Map.of("package", "modules." + module.toLowerCase(), "class", module + "Module"), force);
        context.output().success("Module created: " + context.workingDirectory().relativize(root));
    }
}

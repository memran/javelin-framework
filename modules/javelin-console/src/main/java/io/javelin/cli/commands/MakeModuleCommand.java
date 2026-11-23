package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.Names;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Locale;
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
        // Workspace projects use a flat layout (app/, routes/, modules/), not Maven's src/ tree.
        String module = Names.className(name, "Module");
        Path root = context.workingDirectory().resolve("modules").resolve(module);
        generator().directory(root);
        Path provider = root.resolve(module + ".java");
        generator().write(provider, "module.stub",
                Map.of("package", "modules." + module.toLowerCase(Locale.ROOT), "class", module), force);
        context.output().success("Module created: " + context.workingDirectory().relativize(root));
    }
}

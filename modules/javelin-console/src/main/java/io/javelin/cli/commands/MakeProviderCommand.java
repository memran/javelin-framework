package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.Names;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "make:provider", description = "Create a service provider and its manifest")
public final class MakeProviderCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeProviderCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        String className = Names.className(name, "Provider");
        generate(name, "Provider", "app.providers", context.workingDirectory().resolve("app"), "provider.stub");

        String moduleName = className.replaceAll("Provider$", "")
                .replaceAll("([a-z])([A-Z])", "$1-$2")
                .replaceAll("[^A-Za-z0-9-]", "")
                .toLowerCase();
        Path manifest = context.workingDirectory().resolve("modules").resolve(moduleName)
                .resolve("provider.json");
        generator().write(manifest, "provider-manifest.stub", Map.of(
                "name", moduleName,
                "class", "app.providers." + className,
                "version", "0.1.0"), force);
        context.output().success("Provider created: " + context.workingDirectory().relativize(manifest));
    }
}

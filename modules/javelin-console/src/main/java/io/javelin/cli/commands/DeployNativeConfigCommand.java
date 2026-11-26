package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.deployment.GraalvmConfigGenerator;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "deploy:native-config", description = "Generate GraalVM reachability metadata")
public final class DeployNativeConfigCommand extends AbstractCommand {
    public DeployNativeConfigCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            for (Path file : new GraalvmConfigGenerator().write(context.workingDirectory())) {
                context.output().success("Created: " + context.workingDirectory().relativize(file));
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to write GraalVM metadata", exception);
        }
    }
}

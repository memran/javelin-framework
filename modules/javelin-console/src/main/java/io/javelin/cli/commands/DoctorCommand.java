package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.nio.file.Files;
import java.util.List;

@CommandLine.Command(name = "doctor", description = "Check the local Javelin environment")
public final class DoctorCommand extends AbstractCommand {
    public DoctorCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().line("Java: " + Runtime.version());
        for (String path : List.of("pom.xml", "config/app.yaml", ".env", "storage")) {
            context.output().line((Files.exists(context.workingDirectory().resolve(path)) ? "OK   " : "MISS ") + path);
        }
    }
}

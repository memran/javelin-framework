package io.javelin.cli;

import io.javelin.cli.output.ConsoleOutput;
import io.javelin.core.Application;

import java.nio.file.Path;
import java.util.Optional;

public final class CommandContext {
    private final Application application;
    private final Path workingDirectory;
    private final ConsoleOutput output;

    public CommandContext(Application application, Path workingDirectory, ConsoleOutput output) {
        this.application = application;
        this.workingDirectory = workingDirectory;
        this.output = output;
    }

    public Optional<Application> application() {
        return Optional.ofNullable(application);
    }

    public Application requireApplication() {
        if (application == null) {
            throw new CliException("This command must be run from a Javelin application.",
                    "Run it through your app entry point or execute it inside a generated project.");
        }
        return application;
    }

    public Path workingDirectory() {
        return workingDirectory;
    }

    public ConsoleOutput output() {
        return output;
    }
}

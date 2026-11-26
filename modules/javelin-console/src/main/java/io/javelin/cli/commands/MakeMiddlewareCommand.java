package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:middleware", description = "Create HTTP middleware")
public final class MakeMiddlewareCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeMiddlewareCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        this.force = overwrite;
        Path path = generate(name, "Middleware", "app.middleware",
                context.workingDirectory().resolve("app"), "middleware.stub");
        context.output().success("Middleware created: " + context.workingDirectory().relativize(path));
    }
}

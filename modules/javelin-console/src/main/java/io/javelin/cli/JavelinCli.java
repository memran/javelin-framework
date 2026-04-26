package io.javelin.cli;

import java.nio.file.Path;

public final class JavelinCli {
    private JavelinCli() {
    }

    public static void main(String[] args) {
        try {
            var invocation = ProjectLauncher.invocation(args, Path.of(System.getProperty("user.dir")));
            if (invocation.isPresent()) {
                System.exit(ProjectLauncher.run(invocation.get()));
            }
            System.exit(new ConsoleKernel((io.javelin.core.Application) null).run(args));
        } catch (CliException exception) {
            System.err.println("✘ " + exception.getMessage());
            if (exception.fix() != null) {
                System.err.println("ℹ Fix: " + exception.fix());
            }
            System.exit(1);
        }
    }
}

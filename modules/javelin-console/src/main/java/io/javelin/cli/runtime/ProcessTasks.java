package io.javelin.cli.runtime;

import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ProcessTasks {
    private ProcessTasks() {
    }

    public static void run(CommandContext context, String task, List<String> command) {
        context.output().info(task + ": " + String.join(" ", command));
        try {
            ProcessBuilder builder = new ProcessBuilder(new ArrayList<>(command));
            builder.directory(context.workingDirectory().toFile());
            builder.inheritIO();
            int exit = builder.start().waitFor();
            if (exit != 0) {
                throw new CliException(task + " failed with exit code " + exit);
            }
        } catch (IOException exception) {
            throw new CliException("Unable to start " + command.getFirst(),
                    "Make sure the command is installed and available on PATH.");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new CliException(task + " interrupted.");
        }
    }
}

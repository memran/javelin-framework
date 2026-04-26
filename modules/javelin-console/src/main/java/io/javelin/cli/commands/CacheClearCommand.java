package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.cli.CliException;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@CommandLine.Command(name = "cache:clear", description = "Clear framework cache files")
public final class CacheClearCommand extends AbstractCommand {
    public CacheClearCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path cache = context.workingDirectory().resolve("storage/cache");
        if (!Files.exists(cache)) {
            context.output().success("Cache cleared");
            return;
        }
        try (var stream = Files.walk(cache)) {
            for (Path path : stream.sorted(Comparator.reverseOrder()).toList()) {
                if (!path.equals(cache)) {
                    Files.deleteIfExists(path);
                }
            }
            context.output().success("Cache cleared");
        } catch (IOException exception) {
            throw new CliException("Unable to clear cache: " + cache);
        }
    }
}

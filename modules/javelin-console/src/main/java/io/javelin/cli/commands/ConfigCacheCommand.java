package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "config:cache", description = "Cache application configuration")
public final class ConfigCacheCommand extends AbstractCommand {
    public ConfigCacheCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path cache = context.workingDirectory().resolve("storage/cache/config.cache");
        try {
            Files.createDirectories(cache.getParent());
            Files.writeString(cache, "generated=true%n".formatted());
            context.output().success("Config cached");
        } catch (IOException exception) {
            throw new CliException("Unable to cache config: " + cache);
        }
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "ai:install", description = "Install AI assistant configuration")
public final class AiInstallCommand extends AbstractCommand {
    public AiInstallCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path config = context.workingDirectory().resolve(".javelin/ai.properties");
        try {
            Files.createDirectories(config.getParent());
            if (!Files.exists(config)) {
                Files.writeString(config, """
                        provider=openai-compatible
                        base_url=http://127.0.0.1:11434/v1
                        chat_path=/chat/completions
                        model=
                        api_key=
                        system=
                        timeout_seconds=60
                        """);
            }
            context.output().success("AI tooling installed: " + context.workingDirectory().relativize(config));
        } catch (IOException exception) {
            throw new CliException("Unable to install AI configuration.");
        }
    }
}

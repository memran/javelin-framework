package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "ai:chat", description = "Start an AI chat session")
public final class AiChatCommand extends AbstractCommand {
    public AiChatCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().info("AI chat requires a configured provider in .javelin/ai.properties.");
    }
}

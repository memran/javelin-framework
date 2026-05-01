package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.support.Ai;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@CommandLine.Command(name = "ai:chat", description = "Start an AI chat session")
public final class AiChatCommand extends AbstractCommand {
    @CommandLine.Parameters(index = "0", arity = "0..*", description = "Prompt text")
    List<String> prompt;

    @CommandLine.Option(names = "--stream", description = "Stream tokens as they arrive")
    boolean stream;

    public AiChatCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path config = context.workingDirectory().resolve(".javelin/ai.properties");
        if (!Files.exists(config)) {
            context.output().info("Run `javelin ai:install` first to create .javelin/ai.properties.");
            return;
        }
        if (prompt == null || prompt.isEmpty()) {
            context.output().info("Usage: javelin ai:chat [--stream] <prompt>");
            return;
        }
        String text = String.join(" ", prompt).trim();
        if (text.isBlank()) {
            context.output().info("Usage: javelin ai:chat [--stream] <prompt>");
            return;
        }
        Ai.Handle ai = Ai.from(config);
        if (ai.model().isEmpty()) {
            context.output().info("Set `model=` in .javelin/ai.properties before using `ai:chat`.");
            return;
        }
        if (stream) {
            Ai.Reply reply = ai.stream(text, context.output()::line);
            if (!reply.text().isBlank()) {
                context.output().success(reply.text());
            }
            return;
        }
        Ai.Reply reply = ai.chat(text);
        context.output().success(reply.text());
    }
}

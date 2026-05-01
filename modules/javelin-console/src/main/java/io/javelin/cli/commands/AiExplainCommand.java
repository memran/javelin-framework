package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;
import io.javelin.support.Ai;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "ai:explain", description = "Explain a stacktrace or diagnostic file")
public final class AiExplainCommand extends AbstractCommand {
    @CommandLine.Parameters(index = "0")
    Path file;

    public AiExplainCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path target = file.isAbsolute() ? file : context.workingDirectory().resolve(file);
        try {
            String content = Files.readString(target);
            Path config = context.workingDirectory().resolve(".javelin/ai.properties");
            if (Files.exists(config)) {
                Ai.Handle ai = Ai.from(config);
                if (ai.model().isEmpty()) {
                    context.output().info("AI config exists but `model=` is blank; showing local summary.");
                    content.lines().filter(line -> line.contains("Exception") || line.startsWith("\tat "))
                            .limit(8)
                            .forEach(context.output()::line);
                    return;
                }
                Ai.Reply reply = ai.chat("""
                        Explain the following error and suggest a fix:

                        %s
                        """.formatted(content));
                context.output().success(reply.text());
                return;
            }
            context.output().info("AI provider is not configured; showing local summary.");
            content.lines().filter(line -> line.contains("Exception") || line.startsWith("\tat "))
                    .limit(8)
                    .forEach(context.output()::line);
        } catch (IOException exception) {
            throw new CliException("Unable to read file: " + target);
        }
    }
}

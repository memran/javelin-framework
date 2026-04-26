package io.javelin.cli;

import picocli.CommandLine;

public abstract class AbstractCommand implements Command {
    protected final CommandContext context;

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public String name() {
        CommandLine.Command command = getClass().getAnnotation(CommandLine.Command.class);
        return command == null ? getClass().getSimpleName() : command.name();
    }
}

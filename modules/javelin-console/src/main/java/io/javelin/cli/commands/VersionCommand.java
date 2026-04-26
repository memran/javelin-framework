package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import picocli.CommandLine;

@CommandLine.Command(name = "version", description = "Show CLI version")
public final class VersionCommand extends AbstractCommand {
    public VersionCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        context.output().line(version());
    }

    public static String version() {
        Package pkg = VersionCommand.class.getPackage();
        String value = pkg == null ? null : pkg.getImplementationVersion();
        return "Javelin CLI " + (value == null ? "0.1.0-SNAPSHOT" : value);
    }

    public static final class Provider implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[]{version()};
        }
    }
}

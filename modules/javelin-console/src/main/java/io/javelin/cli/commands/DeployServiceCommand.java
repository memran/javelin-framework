package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.deployment.SystemdUnit;
import picocli.CommandLine;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "deploy:service", description = "Generate a systemd unit file")
public final class DeployServiceCommand extends AbstractCommand {
    @CommandLine.Option(names = "--user", defaultValue = "javelin")
    String user;
    @CommandLine.Option(names = "--app", defaultValue = "javelin")
    String appName;

    public DeployServiceCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            Path root = context.workingDirectory();
            String jar = root.resolve("target").resolve(appName + ".jar").toString();
            String working = root.toString();
            String envFile = root.resolve(".env").toString();
            String content = new SystemdUnit(appName, user, working, jar, "", envFile).render();
            Path file = root.resolve("deploy").resolve(appName + ".service");
            Files.createDirectories(file.getParent());
            Files.writeString(file, content, StandardCharsets.UTF_8);
            context.output().success("Systemd unit created: " + root.relativize(file));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create systemd unit", exception);
        }
    }
}

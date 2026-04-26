package io.javelin.cli;

import io.javelin.cli.commands.AiChatCommand;
import io.javelin.cli.commands.AiExplainCommand;
import io.javelin.cli.commands.AiGenerateModuleCommand;
import io.javelin.cli.commands.AiInstallCommand;
import io.javelin.cli.commands.BuildCommand;
import io.javelin.cli.commands.CacheClearCommand;
import io.javelin.cli.commands.ConfigCacheCommand;
import io.javelin.cli.commands.DevCommand;
import io.javelin.cli.commands.DoctorCommand;
import io.javelin.cli.commands.MakeControllerCommand;
import io.javelin.cli.commands.MakeMigrationCommand;
import io.javelin.cli.commands.MakeModelCommand;
import io.javelin.cli.commands.MakeModuleCommand;
import io.javelin.cli.commands.MakeServiceCommand;
import io.javelin.cli.commands.MigrateCommand;
import io.javelin.cli.commands.MigrateRollbackCommand;
import io.javelin.cli.commands.NativeCommand;
import io.javelin.cli.commands.NewCommand;
import io.javelin.cli.commands.QueueStatusCommand;
import io.javelin.cli.commands.QueueWorkCommand;
import io.javelin.cli.commands.RoutesListCommand;
import io.javelin.cli.commands.ScheduleRunCommand;
import io.javelin.cli.commands.SeedCommand;
import io.javelin.cli.commands.ServeCommand;
import io.javelin.cli.commands.TestCommand;
import io.javelin.cli.commands.VersionCommand;
import io.javelin.cli.output.ConsoleOutput;
import io.javelin.core.Application;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(
        name = "javelin",
        description = "Javelin Framework command line tools",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true,
        versionProvider = VersionCommand.Provider.class,
        header = "@|bold,cyan Javelin Framework|@")
public final class ConsoleKernel implements Runnable {
    @CommandLine.Option(names = "--project", description = "Select a Javelin application project, for example: --project demo-app")
    String project;

    private final CommandContext context;
    private final CommandRegistry registry;

    public ConsoleKernel(Application application) {
        this(new CommandContext(application, workingDirectory(application), new ConsoleOutput()));
    }

    public ConsoleKernel(CommandContext context) {
        this.context = context;
        this.registry = commands(context);
    }

    public int run(String[] args) {
        CommandLine commandLine = new CommandLine(this);
        commandLine.setExecutionExceptionHandler((exception, parsed, parseResult) -> {
            Throwable cause = exception instanceof CliException ? exception : exception.getCause();
            if (cause instanceof CliException cliException) {
                context.output().error(cliException.getMessage());
                if (cliException.fix() != null) {
                    context.output().info("Fix: " + cliException.fix());
                }
                return 1;
            }
            context.output().error(exception.getMessage());
            return 1;
        });
        registry.names().forEach(name -> commandLine.addSubcommand(name, registry.create(name)));
        return commandLine.execute(args);
    }

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }

    private static CommandRegistry commands(CommandContext context) {
        return new CommandRegistry()
                .register("new", () -> new NewCommand(context))
                .register("serve", () -> new ServeCommand(context))
                .register("dev", () -> new DevCommand(context))
                .register("routes:list", () -> new RoutesListCommand(context))
                .register("make:controller", () -> new MakeControllerCommand(context))
                .register("make:service", () -> new MakeServiceCommand(context))
                .register("make:model", () -> new MakeModelCommand(context))
                .register("make:module", () -> new MakeModuleCommand(context))
                .register("make:migration", () -> new MakeMigrationCommand(context))
                .register("migrate", () -> new MigrateCommand(context))
                .register("migrate:rollback", () -> new MigrateRollbackCommand(context))
                .register("seed", () -> new SeedCommand(context))
                .register("test", () -> new TestCommand(context))
                .register("build", () -> new BuildCommand(context))
                .register("native", () -> new NativeCommand(context))
                .register("queue:work", () -> new QueueWorkCommand(context))
                .register("queue:status", () -> new QueueStatusCommand(context))
                .register("schedule:run", () -> new ScheduleRunCommand(context))
                .register("cache:clear", () -> new CacheClearCommand(context))
                .register("config:cache", () -> new ConfigCacheCommand(context))
                .register("doctor", () -> new DoctorCommand(context))
                .register("version", () -> new VersionCommand(context))
                .register("ai:install", () -> new AiInstallCommand(context))
                .register("ai:chat", () -> new AiChatCommand(context))
                .register("ai:generate", () -> new AiGenerateModuleCommand(context))
                .register("ai:explain", () -> new AiExplainCommand(context));
    }

    private static Path workingDirectory(Application application) {
        if (application != null && application.has(Path.class)) {
            return application.make(Path.class);
        }
        return Path.of(System.getProperty("user.dir"));
    }
}

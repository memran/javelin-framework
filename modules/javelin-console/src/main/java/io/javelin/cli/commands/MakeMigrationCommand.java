package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.FileGenerator;
import io.javelin.cli.generator.Names;
import io.javelin.cli.generator.TemplateEngine;
import picocli.CommandLine;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@CommandLine.Command(name = "make:migration", description = "Create a database migration")
public final class MakeMigrationCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeMigrationCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filename = timestamp + "_" + Names.snake(name) + ".sql";
        Path target = context.workingDirectory().resolve("database/migrations").resolve(filename);
        new FileGenerator(new TemplateEngine()).write(target, "migration.stub", Map.of("name", name), overwrite);
        context.output().success("Migration created: " + context.workingDirectory().relativize(target));
    }
}

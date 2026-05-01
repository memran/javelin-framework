package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.CliException;
import io.javelin.cli.generator.FileGenerator;
import io.javelin.cli.generator.Names;
import io.javelin.cli.generator.TemplateEngine;
import picocli.CommandLine;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Locale;

@CommandLine.Command(name = "make:migration", description = "Create a database migration")
public final class MakeMigrationCommand extends GenerateCommandSupport {
    @CommandLine.Parameters(index = "0")
    String name;
    @CommandLine.Option(names = "--type", description = "Migration scaffold type: create-table, add-column, seed", defaultValue = "create-table")
    String type;
    @CommandLine.Option(names = "--table", description = "Target table for the scaffold")
    String table;
    @CommandLine.Option(names = "--column", description = "Target column for the add-column scaffold")
    String column;
    @CommandLine.Option(names = "--datatype", description = "Column datatype for the add-column scaffold", defaultValue = "varchar(255)")
    String datatype;
    @CommandLine.Option(names = "--columns", description = "Column definitions for the create-table scaffold")
    String columns;
    @CommandLine.Option(names = "--force")
    boolean overwrite;

    public MakeMigrationCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filename = timestamp + "_" + Names.snake(name) + ".yml";
        Path target = context.workingDirectory().resolve("database/migrations").resolve(filename);
        new FileGenerator(new TemplateEngine()).write(target, templateFor(type), scaffoldValues(), overwrite);
        context.output().success("Migration created: " + context.workingDirectory().relativize(target));
    }

    private String templateFor(String scaffoldType) {
        return switch (scaffoldType.toLowerCase(Locale.ROOT)) {
            case "create-table" -> "migration-create-table.stub";
            case "add-column" -> "migration-add-column.stub";
            case "seed" -> "migration-seed.stub";
            case "generic", "" -> "migration.stub";
            default -> throw new CliException("Unsupported migration scaffold type: " + scaffoldType,
                    "Use one of: create-table, add-column, seed, or generic.");
        };
    }

    private Map<String, String> scaffoldValues() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("name", name);
        values.put("type", type);
        values.put("table", resolvedTable());
        values.put("column", resolvedColumn());
        values.put("datatype", datatype);
        values.put("columns", resolvedColumns());
        return values;
    }

    private String resolvedTable() {
        if (table != null && !table.isBlank()) {
            return table;
        }
        String inferred = Names.snake(name).replaceFirst("^create_", "").replaceFirst("_table$", "");
        return inferred.isBlank() ? Names.snake(name) : inferred;
    }

    private String resolvedColumn() {
        if (column != null && !column.isBlank()) {
            return column;
        }
        return "name";
    }

    private String resolvedColumns() {
        if (columns != null && !columns.isBlank()) {
            return columns;
        }
        return """
                email varchar(255) not null unique,
                name varchar(255) not null,
                created_at timestamp not null,
                updated_at timestamp not null
                """.stripTrailing();
    }
}

package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.FileGenerator;
import io.javelin.cli.generator.Names;
import io.javelin.cli.generator.TemplateEngine;

import java.nio.file.Path;
import java.util.Map;

abstract class GenerateCommandSupport extends AbstractCommand {
    protected boolean force;
    private final FileGenerator generator = new FileGenerator(new TemplateEngine());

    protected GenerateCommandSupport(CommandContext context) {
        super(context);
    }

    protected Path generate(String rawName, String suffix, String rootPackage, Path rootDirectory, String template) {
        String className = Names.className(rawName, suffix);
        String packageName = Names.packageName(rawName, rootPackage);
        String relativePackage = packageName.startsWith("app.")
                ? packageName.substring("app.".length()).replace('.', '/')
                : packageName.replace('.', '/');
        Path packagePath = rootDirectory.resolve(relativePackage);
        Path file = packagePath.resolve(className + ".java");
        generator.write(file, template, Map.of(
                "package", packageName,
                "class", className,
                "name", rawName,
                "snake", Names.snake(rawName)
        ), force);
        return file;
    }

    protected FileGenerator generator() {
        return generator;
    }
}

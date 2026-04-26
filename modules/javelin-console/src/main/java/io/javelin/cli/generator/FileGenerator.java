package io.javelin.cli.generator;

import io.javelin.cli.CliException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class FileGenerator {
    private final TemplateEngine templates;

    public FileGenerator(TemplateEngine templates) {
        this.templates = templates;
    }

    public Path write(Path target, String template, Map<String, String> values, boolean force) {
        if (Files.exists(target) && !force) {
            throw new CliException("Refusing to overwrite existing file: " + target,
                    "Pass --force when replacing generated files intentionally.");
        }
        try {
            Path parent = target.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(target, templates.render(template, values));
            return target;
        } catch (IOException exception) {
            throw new CliException("Unable to write file: " + target);
        }
    }

    public void directory(Path target) {
        try {
            Files.createDirectories(target);
        } catch (IOException exception) {
            throw new CliException("Unable to create directory: " + target);
        }
    }
}

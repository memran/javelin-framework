package io.javelin.console;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:model", description = "Create a model")
public final class MakeModelCommand implements Runnable {
    @CommandLine.Parameters(index = "0")
    String name;

    @Override
    public void run() {
        GeneratorSupport.write(Path.of("app/models/" + name + ".java"), """
                package app.models;

                public record %s(Long id) {
                }
                """.formatted(name));
    }
}

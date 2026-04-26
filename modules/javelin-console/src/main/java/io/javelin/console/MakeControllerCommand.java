package io.javelin.console;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:controller", description = "Create a controller")
public final class MakeControllerCommand implements Runnable {
    @CommandLine.Parameters(index = "0")
    String name;

    @Override
    public void run() {
        GeneratorSupport.write(Path.of("app/controllers/" + name + ".java"), """
                package app.controllers;

                import io.javelin.core.Request;
                import io.javelin.core.Response;

                public final class %s {
                    public Response index(Request request) {
                        return Response.text("Hello from %s");
                    }
                }
                """.formatted(name, name));
    }
}

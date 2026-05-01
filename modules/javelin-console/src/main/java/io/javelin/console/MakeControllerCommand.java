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
                import java.nio.file.Path;

                public final class %s {
                    public Response index(Request request) {
                        Path saved = request.saveFile("file", Path.of("storage/uploads")).orElseThrow();
                        // Example: user.fill(Map.of("avatar_path", saved.toString())); user.save();
                        return Response.text("Hello from %s");
                    }
                }
                """.formatted(name, name));
    }
}

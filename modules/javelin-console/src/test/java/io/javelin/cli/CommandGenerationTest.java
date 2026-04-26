package io.javelin.cli;

import io.javelin.cli.output.ConsoleOutput;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CommandGenerationTest {
    @Test
    void createsControllerFromTemplate() throws Exception {
        Path root = Files.createTempDirectory("javelin-cli");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:controller", "User"});

        assertEquals(0, exit);
        Path controller = root.resolve("app/controllers/UserController.java");
        assertTrue(Files.exists(controller));
        assertTrue(Files.readString(controller).contains("final class UserController"));
        assertTrue(output.toString().contains("Controller created"));
    }

    @Test
    void createsProjectSkeleton() throws Exception {
        Path root = Files.createTempDirectory("javelin-new");
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(new StringWriter(), true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"new", "crm-app"});

        assertEquals(0, exit);
        Path project = root.resolve("crm-app");
        assertTrue(Files.exists(project.resolve("pom.xml")));
        assertTrue(Files.exists(project.resolve(".env")));
        assertTrue(Files.exists(project.resolve("Main.java")));
        assertTrue(Files.exists(project.resolve("app/controllers/HomeController.java")));
        assertTrue(Files.exists(project.resolve("config/app.yaml")));
        assertTrue(Files.exists(project.resolve("config/view.yml")));
        assertTrue(Files.exists(project.resolve("database/migrations")));
        assertTrue(Files.exists(project.resolve("modules")));
    }

    @Test
    void refusesToOverwriteGeneratedFiles() throws Exception {
        Path root = Files.createTempDirectory("javelin-overwrite");
        Files.createDirectories(root.resolve("app/controllers"));
        Files.writeString(root.resolve("app/controllers/UserController.java"), "existing");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:controller", "User"});

        assertEquals(1, exit);
        assertTrue(Files.readString(root.resolve("app/controllers/UserController.java")).contains("existing"));
        assertTrue(output.toString().contains("Refusing to overwrite"));
    }
}

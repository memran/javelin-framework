package io.javelin.cli;

import io.javelin.cli.output.ConsoleOutput;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
    void createsModelFromTemplate() throws Exception {
        Path root = Files.createTempDirectory("javelin-model");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:model", "User"});

        assertEquals(0, exit);
        Path model = root.resolve("app/models/User.java");
        assertTrue(Files.exists(model));
        assertTrue(Files.readString(model).contains("extends Model"));
        assertTrue(Files.readString(model).contains("public User(Database database)"));
        assertTrue(output.toString().contains("Model created"));
    }

    @Test
    void createsCreateTableYamlMigrationFromTemplate() throws Exception {
        Path root = Files.createTempDirectory("javelin-migration");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:migration", "create_users_table", "--table", "users"});

        assertEquals(0, exit);
        Path migrations = root.resolve("database/migrations");
        try (Stream<Path> files = Files.list(migrations)) {
            Path migration = files.findFirst().orElseThrow();
            assertTrue(migration.getFileName().toString().endsWith(".yml"));
            String contents = Files.readString(migration);
            assertTrue(contents.contains("name: create_users_table"));
            assertTrue(contents.contains("type: create-table"));
            assertTrue(contents.contains("table: users"));
            assertTrue(contents.contains("create table users"));
            assertTrue(contents.contains("up:"));
            assertTrue(contents.contains("down:"));
        }
        assertTrue(output.toString().contains("Migration created"));
    }

    @Test
    void createsAddColumnYamlMigrationFromTemplate() throws Exception {
        Path root = Files.createTempDirectory("javelin-migration-add-column");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:migration", "add_avatar_to_users", "--type", "add-column", "--table", "users", "--column", "avatar_path", "--datatype", "varchar(512)"});

        assertEquals(0, exit);
        try (Stream<Path> files = Files.list(root.resolve("database/migrations"))) {
            Path migration = files.findFirst().orElseThrow();
            String contents = Files.readString(migration);
            assertTrue(contents.contains("type: add-column"));
            assertTrue(contents.contains("table: users"));
            assertTrue(contents.contains("column: avatar_path"));
            assertTrue(contents.contains("alter table users add column avatar_path varchar(512)"));
            assertTrue(contents.contains("alter table users drop column avatar_path"));
        }
    }

    @Test
    void createsSeedYamlMigrationFromTemplate() throws Exception {
        Path root = Files.createTempDirectory("javelin-migration-seed");
        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:migration", "seed_users", "--type", "seed", "--table", "users"});

        assertEquals(0, exit);
        try (Stream<Path> files = Files.list(root.resolve("database/migrations"))) {
            Path migration = files.findFirst().orElseThrow();
            String contents = Files.readString(migration);
            assertTrue(contents.contains("type: seed"));
            assertTrue(contents.contains("table: users"));
            assertTrue(contents.contains("insert into users (name)"));
            assertTrue(contents.contains("delete from users where name = 'Example row'"));
        }
    }

    @Test
    void createsProjectSkeleton() throws Exception {
        Path root = Files.createTempDirectory("javelin-new");
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(new StringWriter(), true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"new", "crm-app"});

        assertEquals(0, exit);
        Path project = root.resolve("crm-app");
        assertTrue(Files.exists(project.resolve("pom.xml")));
        assertTrue(Files.exists(project.resolve("README.md")));
        assertTrue(Files.exists(project.resolve(".env")));
        assertTrue(Files.exists(project.resolve("Main.java")));
        assertTrue(Files.exists(project.resolve("install.sh")));
        assertTrue(Files.exists(project.resolve("install.ps1")));
        assertTrue(Files.exists(project.resolve("app/controllers/HomeController.java")));
        assertTrue(Files.exists(project.resolve("app/validation/AdultAgeRule.java")));
        assertTrue(Files.exists(project.resolve("app/views")));
        assertTrue(Files.exists(project.resolve("config/app.yaml")));
        assertTrue(Files.exists(project.resolve("config/view.yml")));
        assertTrue(Files.exists(project.resolve("routes/web.java")));
        assertTrue(Files.exists(project.resolve("database/migrations")));
        assertTrue(Files.exists(project.resolve("modules")));
    }

    @Test
    void createsProjectSkeletonWithCustomValidationRule() throws Exception {
        Path root = Files.createTempDirectory("javelin-new-custom-rule");
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(new StringWriter(), true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"new", "--validation-rule", "TeenAge", "crm-app"});

        assertEquals(0, exit);
        Path project = root.resolve("crm-app");
        Path rule = project.resolve("app/validation/TeenAgeRule.java");
        assertTrue(Files.exists(rule));
        assertTrue(Files.readString(rule).contains("class TeenAgeRule"));
        assertTrue(Files.readString(rule).contains("return \"teen-age\";"));
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

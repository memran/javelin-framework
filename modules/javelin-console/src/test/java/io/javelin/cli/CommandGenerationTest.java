package io.javelin.cli;

import io.javelin.cli.output.ConsoleOutput;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void createsDockerAndFrontendScaffoldForEveryNewProject() throws Exception {
        Path root = Files.createTempDirectory("javelin-new-frontend");
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(new StringWriter(), true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"new", "crm-app"});

        assertEquals(0, exit);
        Path project = root.resolve("crm-app");

        String compose = Files.readString(project.resolve("docker-compose.yml"));
        assertTrue(compose.contains("services:"));
        assertTrue(compose.contains("postgres:16-alpine"));
        assertTrue(compose.contains("DATABASE_NAME: crm-app"));
        assertTrue(compose.contains("depends_on:"));
        assertTrue(compose.contains("valkey/valkey:8-alpine"));
        assertTrue(compose.contains("VALKEY_URL: redis://valkey:6379"));
        assertTrue(compose.contains("image: nats:2-alpine"));
        assertTrue(compose.contains("NATS_URL: nats://nats:4222"));
        assertTrue(compose.contains("\"-js\""));
        assertTrue(compose.contains("NATS_STREAM: JAVELIN"));
        assertTrue(compose.contains("profiles: [\"nginx\"]"));
        assertTrue(compose.contains("profiles: [\"caddy\"]"));
        assertTrue(compose.contains("./nginx.conf:/etc/nginx/conf.d/default.conf:ro"));
        assertTrue(Files.exists(project.resolve("nginx.conf")));
        assertTrue(Files.exists(project.resolve("Caddyfile")));

        String dockerfile = Files.readString(project.resolve("Dockerfile"));
        assertTrue(dockerfile.contains("FROM node:22-alpine AS assets"));
        assertTrue(dockerfile.contains("RUN sh build.sh assets"));
        assertTrue(dockerfile.contains("RUN sh build.sh java"));
        assertTrue(dockerfile.contains("COPY resources/views ./resources/views"));
        assertTrue(dockerfile.contains("COPY app/views ./app/views"));
        assertTrue(dockerfile.contains("maven:3.9-eclipse-temurin-25"));
        assertTrue(dockerfile.contains("eclipse-temurin:25-jre"));
        assertTrue(dockerfile.contains("crm-app-*.jar"));
        assertTrue(Files.exists(project.resolve(".dockerignore")));

        String packageJson = Files.readString(project.resolve("package.json"));
        assertTrue(packageJson.contains("\"tailwindcss\""));
        assertTrue(packageJson.contains("\"alpinejs\""));
        assertTrue(packageJson.contains("\"esbuild\""));
        assertTrue(packageJson.contains("\"dev\": \"node scripts/dev.mjs\""));
        assertTrue(packageJson.contains("\"dev:bun\": \"bun scripts/dev.mjs\""));

        String welcome = Files.readString(project.resolve("resources/views/welcome.peb"));
        assertTrue(welcome.contains("href=\"/css/app.css\""));
        assertTrue(welcome.contains("src=\"/js/app.js\""));
        assertTrue(welcome.contains("x-data"));
        assertFalse(welcome.contains("cdn.tailwindcss.com"));
        assertTrue(Files.readString(project.resolve("app/controllers/HomeController.java"))
                .contains("View.render(\"welcome\""));

        assertTrue(Files.readString(project.resolve("resources/assets/app.css")).contains("@import \"tailwindcss\";"));
        assertTrue(Files.readString(project.resolve("resources/assets/app.js")).contains("Alpine.start()"));
        assertTrue(Files.readString(project.resolve("scripts/dev.mjs")).contains("--watch"));

        String appYaml = Files.readString(project.resolve("config/app.yaml"));
        assertTrue(appYaml.contains("host: ${DATABASE_HOST:localhost}"));
        assertTrue(appYaml.contains("database: ${DATABASE_NAME:crm-app}"));
        assertTrue(appYaml.contains("static_cache_seconds: 3600"));
        assertTrue(appYaml.contains("request_timeout_seconds: 30"));
        assertTrue(appYaml.contains("idle_timeout_seconds: 60"));
        assertTrue(appYaml.contains("172.16.0.0/12"));
        assertTrue(appYaml.contains("http-driver: netty"));
        assertTrue(appYaml.contains("driver: ${CACHE_DRIVER:memory}"));
        assertTrue(appYaml.contains("url: ${VALKEY_URL:}"));
        assertTrue(appYaml.contains("jetstream: ${NATS_JETSTREAM:false}"));
        assertTrue(appYaml.contains("ValkeyCacheServiceProvider"));
        assertTrue(appYaml.contains("NatsQueueServiceProvider"));
        assertTrue(appYaml.contains("ExceptionHandlerServiceProvider"));
        assertTrue(appYaml.contains("request_logging: true"));
        assertTrue(appYaml.contains("correlation_id_header: X-Correlation-ID"));
        assertTrue(appYaml.contains("redact_keys:"));

        String generatedPom = Files.readString(project.resolve("pom.xml"));
        assertTrue(generatedPom.contains("maven-shade-plugin"));
        assertTrue(generatedPom.contains("javelin-http-netty"));
        assertTrue(generatedPom.contains("javelin-cache-valkey"));
        assertTrue(generatedPom.contains("javelin-queue-nats"));
        assertTrue(generatedPom.contains("javelin-exception"));
        assertTrue(generatedPom.contains("<include>errors/**</include>"));
        assertTrue(Files.exists(project.resolve("logback.xml")));
        assertTrue(Files.exists(project.resolve("logback-prod.xml")));
        assertTrue(Files.readString(project.resolve("logback-prod.xml"))
                .contains("io.javelin.log.slf4j.JsonLogEncoder"));
        assertTrue(Files.exists(project.resolve("errors/500.html")));
        assertTrue(Files.readString(project.resolve("Dockerfile")).contains("COPY errors ./errors"));
        String buildScript = Files.readString(project.resolve("build.sh"));
        assertTrue(buildScript.contains("command -v bun"));
        assertTrue(buildScript.contains("mvn -B -q -DskipTests package"));
        assertTrue(Files.exists(project.resolve("build.ps1")));
        assertTrue(Files.readString(project.resolve("README.md")).contains("docker compose up --build"));
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
    void createsModulesInFlatLayoutWithoutSrcDirectory() throws Exception {
        Path root = Files.createTempDirectory("javelin-module");
        CommandContext context = new CommandContext(null, root, new ConsoleOutput(new PrintWriter(new StringWriter(), true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"make:module", "Payment"});

        assertEquals(0, exit);
        Path moduleDir = root.resolve("modules/PaymentModule");
        Path provider = moduleDir.resolve("PaymentModule.java");
        assertTrue(Files.exists(provider));
        assertFalse(Files.exists(moduleDir.resolve("src")));
        String contents = Files.readString(provider);
        assertTrue(contents.contains("package modules.paymentmodule;"));
        assertTrue(contents.contains("class PaymentModule implements ServiceProvider"));
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

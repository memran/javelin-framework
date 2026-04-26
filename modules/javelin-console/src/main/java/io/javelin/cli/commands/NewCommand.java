package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;
import io.javelin.cli.generator.FileGenerator;
import io.javelin.cli.generator.Names;
import io.javelin.cli.generator.TemplateEngine;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "new", description = "Create a new Javelin application")
public final class NewCommand extends AbstractCommand {
    @CommandLine.Parameters(index = "0", description = "Project directory name")
    String name;
    @CommandLine.Option(names = "--force", description = "Allow writing into an existing empty directory")
    boolean force;

    public NewCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        Path root = context.workingDirectory().resolve(name).normalize();
        if (Files.exists(root) && !force) {
            throw new CliException("Project directory already exists: " + root,
                    "Choose another name or pass --force for an existing empty directory.");
        }
        FileGenerator files = new FileGenerator(new TemplateEngine());
        files.directory(root.resolve("app/controllers"));
        files.directory(root.resolve("app/providers"));
        files.directory(root.resolve("config"));
        files.directory(root.resolve("routes"));
        files.directory(root.resolve("resources/views"));
        files.directory(root.resolve("storage/logs"));
        files.directory(root.resolve("database/migrations"));
        files.directory(root.resolve("database/seeders"));
        files.directory(root.resolve("tests"));
        files.directory(root.resolve("modules"));
        Map<String, String> values = Map.of("project", name, "class", Names.className(name, ""));
        files.write(root.resolve("pom.xml"), "project-pom.stub", values, force);
        files.write(root.resolve(".env"), "env.stub", values, force);
        files.write(root.resolve("Main.java"), "main.stub", values, force);
        files.write(root.resolve("config/app.yaml"), "app-yaml.stub", values, force);
        files.write(root.resolve("config/view.yml"), "view-yml.stub", values, force);
        files.write(root.resolve("app/controllers/HomeController.java"), "home-controller.stub", values, force);
        files.write(root.resolve("app/providers/AppServiceProvider.java"), "app-provider.stub", values, force);
        files.write(root.resolve("routes/web.java"), "routes.stub", values, force);
        context.output().success("Project created: " + context.workingDirectory().relativize(root));
        context.output().info("Next: cd " + name + " && mvn test");
    }
}

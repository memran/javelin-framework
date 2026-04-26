package io.javelin.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilderFactory;

final class ProjectLauncher {
    private static final List<String> APP_COMMANDS = List.of(
            "serve",
            "dev",
            "routes:list",
            "migrate",
            "migrate:rollback",
            "seed",
            "queue:work",
            "queue:status",
            "schedule:run",
            "cache:clear",
            "config:cache",
            "doctor",
            "build"
    );

    private ProjectLauncher() {
    }

    static Optional<Invocation> invocation(String[] args, Path cwd) {
        ParsedArgs parsed = parse(args);
        Path project = parsed.project()
                .map(value -> cwd.resolve(value).normalize())
                .or(() -> defaultProject(parsed.remaining(), cwd))
                .orElse(null);
        if (project == null) {
            return Optional.empty();
        }
        return Optional.of(new Invocation(project, parsed.remaining()));
    }

    static int run(Invocation invocation) {
        Path project = invocation.project().toAbsolutePath().normalize();
        if (!Files.exists(project.resolve("pom.xml"))) {
            throw new CliException("Project is missing pom.xml: " + project);
        }
        if (invocation.remaining().length == 1 && "build".equals(invocation.remaining()[0])) {
            BuildPlan build = buildPlan(project);
            return runProcess(build.directory(), build.command());
        }
        Path jar = runnableJar(project);
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add(jar.toString());
        command.addAll(List.of(invocation.remaining()));
        return runProcess(project, command);
    }

    private static BuildPlan buildPlan(Path project) {
        Path parent = project.getParent();
        if (parent != null && Files.exists(parent.resolve("pom.xml"))) {
            return new BuildPlan(parent, List.of(mavenCommand(), "-pl", project.getFileName().toString(), "-am", "package", "-DskipTests"));
        }
        return new BuildPlan(project, List.of(mavenCommand(), "package", "-DskipTests"));
    }

    private static ParsedArgs parse(String[] args) {
        List<String> remaining = new ArrayList<>();
        String project = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ("--project".equals(arg)) {
                if (i + 1 >= args.length) {
                    throw new CliException("--project requires a path.");
                }
                project = args[++i];
            } else if (arg.startsWith("--project=")) {
                project = arg.substring("--project=".length());
            } else {
                remaining.add(arg);
            }
        }
        return new ParsedArgs(Optional.ofNullable(project), remaining.toArray(String[]::new));
    }

    private static Optional<Path> defaultProject(String[] args, Path cwd) {
        if (args.length == 0 || !APP_COMMANDS.contains(args[0])) {
            return Optional.empty();
        }
        if (Files.exists(cwd.resolve("pom.xml")) && Files.exists(cwd.resolve("config/app.yaml"))) {
            return Optional.of(cwd);
        }
        Path demo = cwd.resolve("demo-app");
        if (Files.exists(demo.resolve("pom.xml"))) {
            return Optional.of(demo);
        }
        return Optional.empty();
    }

    private static Path runnableJar(Path project) {
        String artifactId = artifactId(project.resolve("pom.xml"));
        Path target = project.resolve("target");
        try (var stream = Files.list(target)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(artifactId + "-"))
                    .filter(path -> path.getFileName().toString().endsWith(".jar"))
                    .filter(path -> !path.getFileName().toString().startsWith("original-"))
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()))
                    .orElseThrow(() -> new CliException("Unable to find runnable jar in " + target,
                            "Run: javelin --project " + project.getFileName() + " build"));
        } catch (IOException exception) {
            throw new CliException("Unable to inspect build output: " + target);
        }
    }

    private static String artifactId(Path pom) {
        try {
            var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pom.toFile());
            var nodes = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                var node = nodes.item(i);
                if ("artifactId".equals(node.getNodeName())) {
                    return node.getTextContent().trim();
                }
            }
            throw new CliException("Unable to find artifactId in " + pom);
        } catch (Exception exception) {
            if (exception instanceof CliException cliException) {
                throw cliException;
            }
            throw new CliException("Unable to parse pom.xml: " + pom);
        }
    }

    private static int runProcess(Path directory, List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(directory.toFile());
            builder.inheritIO();
            return builder.start().waitFor();
        } catch (IOException exception) {
            throw new CliException("Unable to start " + command.getFirst(),
                    "Make sure it is installed and available on PATH.");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return 130;
        }
    }

    private static String mavenCommand() {
        return System.getProperty("os.name", "").toLowerCase().contains("win") ? "mvn.cmd" : "mvn";
    }

    record Invocation(Path project, String[] remaining) {
    }

    private record BuildPlan(Path directory, List<String> command) {
    }

    private record ParsedArgs(Optional<String> project, String[] remaining) {
    }
}

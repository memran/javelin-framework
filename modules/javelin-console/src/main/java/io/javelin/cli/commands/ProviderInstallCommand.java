package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CliException;
import io.javelin.cli.CommandContext;
import io.javelin.providers.ProviderManifest;
import picocli.CommandLine;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@CommandLine.Command(name = "provider:install", description = "Install a provider from modules/ or Maven coordinates")
public final class ProviderInstallCommand extends AbstractCommand {
    @CommandLine.Parameters(index = "0", description = "Provider name or group:artifact:version")
    String target;

    public ProviderInstallCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            ProviderManifest manifest;
            if (target.split(":").length == 3) {
                manifest = installFromMaven();
            } else {
                manifest = localManifest();
            }
            enableProvider(manifest.providerClass());
            context.output().success("Provider installed: " + manifest.name()
                    + " (" + manifest.version() + ")");
        } catch (CliException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CliException("Unable to install provider " + target + ": " + exception.getMessage());
        }
    }

    private ProviderManifest localManifest() throws Exception {
        Path file = context.workingDirectory().resolve("modules").resolve(target)
                .resolve(ProviderManifest.FILE);
        if (!Files.isRegularFile(file)) {
            throw new CliException("No provider manifest at " + file,
                    "Run make:provider first or pass group:artifact:version.");
        }
        return ProviderManifest.fromJson(Files.readString(file, StandardCharsets.UTF_8));
    }

    private ProviderManifest installFromMaven() throws Exception {
        String[] parts = target.split(":", 3);
        String group = parts[0];
        String artifact = parts[1];
        String version = parts[2];
        Path local = Path.of(System.getProperty("user.home"), ".m2", "repository")
                .resolve(group.replace('.', '/')).resolve(artifact).resolve(version)
                .resolve(artifact + "-" + version + ".jar");
        if (!Files.isRegularFile(local)) {
            throw new CliException("Provider jar not found: " + local,
                    "Run mvn dependency:get -Dartifact=" + target + " first.");
        }
        Path destination = context.workingDirectory().resolve("modules").resolve(artifact);
        Files.createDirectories(destination);
        Path copied = destination.resolve(local.getFileName().toString());
        Files.copy(local, copied, StandardCopyOption.REPLACE_EXISTING);

        ProviderManifest manifest;
        try (JarFile jar = new JarFile(copied.toFile())) {
            JarEntry entry = jar.getJarEntry(ProviderManifest.RESOURCE);
            if (entry == null) {
                throw new CliException("Provider jar has no " + ProviderManifest.RESOURCE,
                        "Add a manifest to the provider jar.");
            }
            try (InputStream stream = jar.getInputStream(entry)) {
                manifest = ProviderManifest.fromJson(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
            }
        }
        addDependency(group, artifact, version);
        return manifest;
    }

    private void enableProvider(String providerClass) throws Exception {
        Path file = context.workingDirectory().resolve("config").resolve("app.yaml");
        String[] lines = Files.readString(file, StandardCharsets.UTF_8).split("\\R", -1);
        List<String> updated = new ArrayList<>();
        int providersLine = -1;
        for (int index = 0; index < lines.length; index++) {
            if (lines[index].trim().equals("providers:")) {
                providersLine = index;
                break;
            }
        }
        if (providersLine < 0) {
            throw new CliException("No providers: section in " + file);
        }
        for (String line : lines) {
            if (line.contains(providerClass)) {
                return;
            }
        }
        int insertAt = providersLine + 1;
        while (insertAt < lines.length && lines[insertAt].startsWith("  - ")) {
            insertAt++;
        }
        for (int index = 0; index < lines.length; index++) {
            updated.add(lines[index]);
            if (index == insertAt - 1) {
                updated.add("  - " + providerClass);
            }
        }
        Files.writeString(file, String.join(System.lineSeparator(), updated), StandardCharsets.UTF_8);
    }

    private void addDependency(String group, String artifact, String version) throws Exception {
        Path pom = context.workingDirectory().resolve("pom.xml");
        String text = Files.readString(pom, StandardCharsets.UTF_8);
        if (text.contains("<artifactId>" + artifact + "</artifactId>")) {
            return;
        }
        String dependency = String.join(System.lineSeparator(),
                "        <dependency>",
                "            <groupId>" + group + "</groupId>",
                "            <artifactId>" + artifact + "</artifactId>",
                "            <version>" + version + "</version>",
                "        </dependency>");
        Files.writeString(pom, text.replace("</dependencies>", dependency + System.lineSeparator()
                + "    </dependencies>"), StandardCharsets.UTF_8);
    }
}

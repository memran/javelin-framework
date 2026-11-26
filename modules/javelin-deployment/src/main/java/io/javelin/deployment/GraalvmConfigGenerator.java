package io.javelin.deployment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Writes starter reachability metadata for GraalVM native images. */
public final class GraalvmConfigGenerator {
    private static final String REFLECT = """
            [
              {
                "name": "io.javelin.core.Application",
                "allDeclaredMethods": true,
                "allDeclaredConstructors": true
              },
              {
                "name": "io.javelin.core.ProviderLoader",
                "allDeclaredMethods": true,
                "allDeclaredConstructors": true
              }
            ]
            """;

    private static final String RESOURCES = """
            {
              "resources": [
                { "pattern": "config/.*" },
                { "pattern": "logback.*xml" },
                { "pattern": "resources/views/.*" },
                { "pattern": "META-INF/javelin/provider.json" }
              ]
            }
            """;

    public List<Path> write(Path root) throws IOException {
        Path directory = Files.createDirectories(
                root.resolve("resources").resolve("META-INF").resolve("native-image").resolve("javelin"));
        Path reflect = directory.resolve("reflect-config.json");
        Path resources = directory.resolve("resource-config.json");
        Files.writeString(reflect, REFLECT, StandardCharsets.UTF_8);
        Files.writeString(resources, RESOURCES, StandardCharsets.UTF_8);
        return List.of(reflect, resources);
    }
}

package io.javelin.deployment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Writes starter reachability metadata for GraalVM native images, plus explicit application entries. */
public final class GraalvmConfigGenerator {
    private static final List<String> BASE_CLASSES = List.of(
            "io.javelin.core.Application",
            "io.javelin.core.ProviderLoader");
    private static final List<String> BASE_RESOURCES = List.of(
            "config/.*",
            "logback.*xml",
            "resources/views/.*",
            "META-INF/javelin/provider.json");

    public List<Path> write(Path root) throws IOException {
        return write(root, List.of(), List.of());
    }

    /** Writes metadata with additional application classes and resource patterns. */
    public List<Path> write(Path root, List<String> classes, List<String> resources) throws IOException {
        Path directory = Files.createDirectories(
                root.resolve("resources").resolve("META-INF").resolve("native-image").resolve("javelin"));
        Path reflect = directory.resolve("reflect-config.json");
        Path resource = directory.resolve("resource-config.json");
        Files.writeString(reflect, reflectConfig(classes), StandardCharsets.UTF_8);
        Files.writeString(resource, resourceConfig(resources), StandardCharsets.UTF_8);
        return List.of(reflect, resource);
    }

    private static String reflectConfig(List<String> classes) {
        List<String> all = new ArrayList<>(BASE_CLASSES);
        all.addAll(classes);
        StringBuilder result = new StringBuilder("[\n");
        for (int index = 0; index < all.size(); index++) {
            result.append("  {\"name\": \"").append(escape(all.get(index)))
                    .append("\", \"allDeclaredMethods\": true, \"allDeclaredConstructors\": true}");
            result.append(index + 1 < all.size() ? ",\n" : "\n");
        }
        return result.append("]\n").toString();
    }

    private static String resourceConfig(List<String> resources) {
        List<String> all = new ArrayList<>(BASE_RESOURCES);
        all.addAll(resources);
        StringBuilder result = new StringBuilder("{\n  \"resources\": [\n");
        for (int index = 0; index < all.size(); index++) {
            result.append("    {\"pattern\": \"").append(escape(all.get(index))).append("\"}");
            result.append(index + 1 < all.size() ? ",\n" : "\n");
        }
        return result.append("  ]\n}\n").toString();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

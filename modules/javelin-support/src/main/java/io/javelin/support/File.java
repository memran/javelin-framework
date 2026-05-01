package io.javelin.support;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

public final class File {
    private File() {
    }

    public static Path ensureDirectory(Path directory) {
        Objects.requireNonNull(directory, "directory");
        try {
            return Files.createDirectories(directory);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create directory: " + directory, exception);
        }
    }

    public static Path ensureParentDirectory(Path path) {
        Objects.requireNonNull(path, "path");
        Path parent = path.getParent();
        if (parent != null) {
            ensureDirectory(parent);
        }
        return path;
    }

    public static String readString(Path path) {
        Objects.requireNonNull(path, "path");
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read file: " + path, exception);
        }
    }

    public static void writeString(Path path, CharSequence content) {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(content, "content");
        ensureParentDirectory(path);
        try {
            Files.writeString(path, content, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write file: " + path, exception);
        }
    }

    public static String extension(Path path) {
        Objects.requireNonNull(path, "path");
        String name = fileName(path);
        int dot = name.lastIndexOf('.');
        if (dot <= 0 || dot == name.length() - 1) {
            return "";
        }
        return name.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    public static String baseName(Path path) {
        Objects.requireNonNull(path, "path");
        String name = fileName(path);
        int dot = name.lastIndexOf('.');
        if (dot <= 0) {
            return name;
        }
        return name.substring(0, dot);
    }

    public static boolean hasExtension(Path path, String extension) {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(extension, "extension");
        String normalized = extension.startsWith(".") ? extension.substring(1) : extension;
        return extension(path).equals(normalized.toLowerCase(Locale.ROOT));
    }

    public static Path safeResolve(Path baseDirectory, String... segments) {
        Objects.requireNonNull(baseDirectory, "baseDirectory");
        Objects.requireNonNull(segments, "segments");

        Path root = baseDirectory.toAbsolutePath().normalize();
        Path resolved = root;
        for (String segment : segments) {
            Objects.requireNonNull(segment, "segment");
            resolved = resolved.resolve(segment);
        }
        resolved = resolved.toAbsolutePath().normalize();
        if (!resolved.startsWith(root)) {
            throw new IllegalArgumentException("Resolved path escapes base directory: " + resolved);
        }
        return resolved;
    }

    private static String fileName(Path path) {
        Path fileName = path.getFileName();
        return fileName == null ? "" : fileName.toString();
    }
}

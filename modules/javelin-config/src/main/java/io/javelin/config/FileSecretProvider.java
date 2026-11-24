package io.javelin.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/** Reads one secret per file from an explicit directory. */
public final class FileSecretProvider implements SecretProvider {
    private final Path directory;

    public FileSecretProvider(Path directory) {
        this.directory = Objects.requireNonNull(directory, "directory").toAbsolutePath().normalize();
    }

    @Override
    public Optional<String> get(String name) {
        Path file = directory.resolve(name).normalize();
        if (!file.startsWith(directory) || !Files.isRegularFile(file)) {
            return Optional.empty();
        }
        try {
            return Optional.of(Files.readString(file, StandardCharsets.UTF_8).trim()).filter(value -> !value.isBlank());
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}

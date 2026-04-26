package io.javelin.config;

import io.javelin.core.Env;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class DotenvEnv implements Env {
    private final Map<String, String> values;

    private DotenvEnv(Map<String, String> values) {
        this.values = Map.copyOf(values);
    }

    public static DotenvEnv load(Path path) {
        Map<String, String> values = new HashMap<>(System.getenv());
        if (Files.exists(path)) {
            try {
                for (String line : Files.readAllLines(path)) {
                    parseLine(line).ifPresent(entry -> values.put(entry.key(), entry.value()));
                }
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to load .env from " + path, exception);
            }
        }
        return new DotenvEnv(values);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(values.get(key));
    }

    private static Optional<Entry> parseLine(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
            return Optional.empty();
        }
        String[] parts = trimmed.split("=", 2);
        if (parts.length != 2) {
            return Optional.empty();
        }
        return Optional.of(new Entry(parts[0].trim(), unquote(parts[1].trim())));
    }

    private static String unquote(String value) {
        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private record Entry(String key, String value) {
    }
}

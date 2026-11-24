package io.javelin.exception;

import io.javelin.core.HtmlResponse;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/** Loads {@code <status>.html} (or {@code error.html}) from an explicit directory. */
public final class FileErrorPageResolver implements ErrorPageResolver {
    private final Path directory;

    public FileErrorPageResolver(Path directory) {
        this.directory = Objects.requireNonNull(directory, "directory");
    }

    @Override
    public Optional<Response> resolve(int status, Request request) {
        Path specific = directory.resolve(status + ".html");
        Path candidate = Files.isRegularFile(specific) ? specific : directory.resolve("error.html");
        if (!Files.isRegularFile(candidate)) {
            return Optional.empty();
        }
        try {
            return Optional.of(new HtmlResponse(Files.readString(candidate, StandardCharsets.UTF_8), status));
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}

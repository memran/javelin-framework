package io.javelin.core;

import io.javelin.support.File;
import io.javelin.support.Security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class UploadedFile {
    private final String fieldName;
    private final String clientFilename;
    private final String contentType;
    private final byte[] body;

    UploadedFile(String fieldName, String clientFilename, String contentType, byte[] body) {
        this.fieldName = Objects.requireNonNull(fieldName, "fieldName");
        this.clientFilename = clientFilename == null ? "" : clientFilename;
        this.contentType = contentType == null || contentType.isBlank() ? "application/octet-stream" : contentType;
        this.body = body == null ? new byte[0] : body.clone();
    }

    public String fieldName() {
        return fieldName;
    }

    public String clientFilename() {
        return clientFilename;
    }

    public String safeFilename() {
        return Security.sanitizeFilename(clientFilename.isBlank() ? "file" : clientFilename);
    }

    public String contentType() {
        return contentType;
    }

    public long size() {
        return body.length;
    }

    public byte[] body() {
        return body.clone();
    }

    public Path saveTo(Path target) {
        Objects.requireNonNull(target, "target");
        File.ensureParentDirectory(target);
        try {
            return Files.write(target, body);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to save uploaded file to " + target, exception);
        }
    }

    public Path saveToDirectory(Path directory) {
        Objects.requireNonNull(directory, "directory");
        return saveTo(File.safeResolve(directory, safeFilename()));
    }
}

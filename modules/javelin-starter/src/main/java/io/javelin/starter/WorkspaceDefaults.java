package io.javelin.starter;

import java.nio.file.Path;
import java.util.Objects;

public record WorkspaceDefaults(Path root, Path migrationsDirectory, Path uploadsDirectory, Path staticDirectory, long maxRequestBytes) {
    public WorkspaceDefaults {
        Objects.requireNonNull(root, "root");
        Objects.requireNonNull(migrationsDirectory, "migrationsDirectory");
        Objects.requireNonNull(uploadsDirectory, "uploadsDirectory");
        Objects.requireNonNull(staticDirectory, "staticDirectory");
    }
}

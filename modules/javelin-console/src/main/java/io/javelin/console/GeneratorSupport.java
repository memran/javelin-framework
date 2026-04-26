package io.javelin.console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class GeneratorSupport {
    private GeneratorSupport() {
    }

    static void write(Path path, String content) {
        try {
            Files.createDirectories(path.getParent());
            if (Files.exists(path)) {
                throw new IllegalStateException(path + " already exists");
            }
            Files.writeString(path, content);
            System.out.println("Created " + path);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write " + path, exception);
        }
    }
}

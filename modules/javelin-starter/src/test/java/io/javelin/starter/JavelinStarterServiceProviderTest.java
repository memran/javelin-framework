package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Env;
import io.javelin.support.ValidationRuleRegistry;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class JavelinStarterServiceProviderTest {
    @Test
    void registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot() throws Exception {
        Path root = Files.createTempDirectory("javelin-starter");
        Application app = new Application(new TestConfig(), new TestEnv());
        app.instance(Path.class, root);
        app.register(new JavelinStarterServiceProvider());

        app.boot();

        WorkspaceDefaults defaults = app.make(WorkspaceDefaults.class);
        assertEquals(root, defaults.root());
        assertEquals(root.resolve("database/migrations"), defaults.migrationsDirectory());
        assertEquals(root.resolve("storage/uploads"), defaults.uploadsDirectory());
        assertEquals(root.resolve("public"), defaults.staticDirectory());
        assertEquals(1_048_576L, defaults.maxRequestBytes());

        ValidationRuleRegistry registry = app.make(ValidationRuleRegistry.class);
        assertTrue(registry.find("workspace-starter").isPresent());
    }

    private static final class TestConfig implements Config {
        @Override
        public Optional<String> getString(String key) {
            return switch (key) {
                case "database.migrations_dir" -> Optional.of("database/migrations");
                case "storage.upload_dir" -> Optional.of("storage/uploads");
                case "server.static_dir" -> Optional.of("public");
                default -> Optional.empty();
            };
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return "security.max_request_bytes".equals(key) ? Optional.of(1_048_576) : Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public List<String> getStringList(String key) {
            return "validation.rules".equals(key)
                    ? List.of("io.javelin.starter.WorkspaceStarterRule")
                    : List.of();
        }
    }

    private static final class TestEnv implements Env {
        @Override
        public Optional<String> get(String key) {
            return Optional.empty();
        }
    }
}

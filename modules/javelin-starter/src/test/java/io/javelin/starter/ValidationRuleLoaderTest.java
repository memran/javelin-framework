package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Env;
import io.javelin.support.Input;
import io.javelin.support.ValidationRuleRegistry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ValidationRuleLoaderTest {
    @Test
    void loadsRulesIntoRegistryFromAppFolderAndConfiguredClassNames() throws IOException {
        Path root = Files.createTempDirectory("javelin-validation-loader");
        Files.createDirectories(root.resolve("app/validation"));
        Files.writeString(root.resolve("app/validation/WorkspaceAgeRule.java"), "package app.validation;");
        Files.writeString(root.resolve("app/validation/AbstractWorkspaceRule.java"), "package app.validation;");

        Application app = new Application(new TestConfig(), new TestEnv());
        app.instance(Path.class, root);

        new ValidationRuleLoader().load(app);

        ValidationRuleRegistry registry = app.make(ValidationRuleRegistry.class);
        assertTrue(registry.find("workspace-age").isPresent());
        assertFalse(registry.find("abstract-workspace").isPresent());
        assertEquals("workspace-age", registry.all().get(0).name());
        assertTrue(registry.find("workspace-age").orElseThrow().validate(Input.from(Map.of("age", "21"))).isEmpty());
    }

    @Test
    void loadsConfiguredRulesWhenAppFolderIsEmpty() {
        Application app = new Application(new EmptyValidationConfig(), new TestEnv());
        app.instance(Path.class, Path.of("."));

        new ValidationRuleLoader().load(app);

        ValidationRuleRegistry registry = app.make(ValidationRuleRegistry.class);
        assertTrue(registry.find("workspace-age").isPresent());
    }

    private static final class TestConfig implements Config {
        @Override
        public Optional<String> getString(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public List<String> getStringList(String key) {
            return List.of();
        }
    }

    private static final class EmptyValidationConfig implements Config {
        @Override
        public Optional<String> getString(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public List<String> getStringList(String key) {
            return "validation.rules".equals(key)
                    ? List.of("app.validation.WorkspaceAgeRule")
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

package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.support.ValidationRule;
import io.javelin.support.ValidationRuleRegistry;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class ValidationRuleLoader {
    public void load(Application app) {
        Objects.requireNonNull(app, "app");
        ValidationRuleRegistry registry = new ValidationRuleRegistry();
        Path root = app.make(Path.class);
        for (String className : discoverRules(root, app.config().getStringList("validation.rules"))) {
            ValidationRule rule = instantiate(className);
            if (rule != null) {
                registry.register(rule);
            }
        }
        app.instance(ValidationRuleRegistry.class, registry);
    }

    private Set<String> discoverRules(Path root, Iterable<String> configuredRules) {
        Set<String> classNames = new LinkedHashSet<>();
        if (root != null) {
            Path validationDirectory = root.resolve("app/validation");
            if (Files.isDirectory(validationDirectory)) {
                try (var stream = Files.walk(validationDirectory)) {
                    stream.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                            .map(validationDirectory::relativize)
                            .map(Path::toString)
                            .map(this::toClassName)
                            .forEach(classNames::add);
                } catch (IOException exception) {
                    throw new IllegalStateException("Unable to scan validation rules from " + validationDirectory, exception);
                }
            }
        }
        for (String className : configuredRules) {
            if (className != null && !className.isBlank()) {
                classNames.add(className.trim());
            }
        }
        return classNames;
    }

    private String toClassName(String relativePath) {
        String normalized = relativePath.replace('\\', '.').replace('/', '.');
        if (normalized.endsWith(".java")) {
            normalized = normalized.substring(0, normalized.length() - 5);
        }
        if (normalized.isBlank()) {
            throw new IllegalStateException("Invalid validation rule source path");
        }
        return "app.validation." + normalized;
    }

    private ValidationRule instantiate(String className) {
        try {
            Class<?> ruleClass = Class.forName(className);
            if (!ValidationRule.class.isAssignableFrom(ruleClass)) {
                throw new IllegalStateException(className + " does not extend ValidationRule");
            }
            if (Modifier.isAbstract(ruleClass.getModifiers())) {
                return null;
            }
            var constructor = ruleClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (ValidationRule) constructor.newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to load validation rule " + className, exception);
        }
    }
}

package io.javelin.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Validation {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final Input input;
    private final ValidationRuleRegistry registry;
    private final Map<String, List<String>> errors = new LinkedHashMap<>();

    private Validation(Input input, ValidationRuleRegistry registry) {
        this.input = Objects.requireNonNull(input, "input");
        this.registry = registry;
    }

    public static Validation of(Input input) {
        return new Validation(input, null);
    }

    public static Validation of(Input input, ValidationRuleRegistry registry) {
        return new Validation(input, Objects.requireNonNull(registry, "registry"));
    }

    public Validation required(String key) {
        String value = input.text(key).orElse(null);
        if (Str.isBlank(value)) {
            addError(key, "is required");
        }
        return this;
    }

    public Validation requiredIf(String key, boolean condition) {
        if (condition) {
            required(key);
        }
        return this;
    }

    public Validation minLength(String key, int minInclusive) {
        String value = input.text(key).orElse(null);
        if (value == null || value.length() < minInclusive) {
            addError(key, "must be at least " + minInclusive + " characters");
        }
        return this;
    }

    public Validation maxLength(String key, int maxInclusive) {
        String value = input.text(key).orElse(null);
        if (value != null && value.length() > maxInclusive) {
            addError(key, "must be at most " + maxInclusive + " characters");
        }
        return this;
    }

    public Validation length(String key, int minInclusive, int maxInclusive) {
        String value = input.text(key).orElse(null);
        if (value == null) {
            addError(key, "is required");
            return this;
        }
        try {
            Validator.requireLength(value, minInclusive, maxInclusive, key + " must be between " + minInclusive + " and " + maxInclusive + " characters");
        } catch (IllegalArgumentException exception) {
            addError(key, exception.getMessage());
        }
        return this;
    }

    public Validation integer(String key) {
        String value = input.text(key).orElse(null);
        if (value == null) {
            addError(key, "is required");
            return this;
        }
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            addError(key, "must be an integer");
        }
        return this;
    }

    public Validation between(String key, int minInclusive, int maxInclusive) {
        String value = input.text(key).orElse(null);
        if (value == null) {
            addError(key, "is required");
            return this;
        }
        try {
            Validator.requireBetween(Integer.parseInt(value), minInclusive, maxInclusive, key + " must be between " + minInclusive + " and " + maxInclusive);
        } catch (RuntimeException exception) {
            addError(key, exception.getMessage());
        }
        return this;
    }

    public Validation matches(String key, Pattern pattern) {
        Objects.requireNonNull(pattern, "pattern");
        String value = input.text(key).orElse(null);
        if (value == null) {
            addError(key, "is required");
            return this;
        }
        try {
            Validator.requireMatches(value, pattern, key + " is invalid");
        } catch (IllegalArgumentException exception) {
            addError(key, exception.getMessage());
        }
        return this;
    }

    public Validation email(String key) {
        return matches(key, EMAIL);
    }

    public Validation oneOf(String key, Collection<? extends String> allowed) {
        Objects.requireNonNull(allowed, "allowed");
        String value = input.text(key).orElse(null);
        if (value == null) {
            addError(key, "is required");
            return this;
        }
        try {
            Validator.requireOneOf(value, allowed, key + " is invalid");
        } catch (IllegalArgumentException exception) {
            addError(key, exception.getMessage());
        }
        return this;
    }

    public Validation custom(ValidationRule rule) {
        Objects.requireNonNull(rule, "rule");
        rule.validate(input).ifPresent(message -> addError(rule.key(), message));
        return this;
    }

    public Validation rule(String name) {
        if (registry == null) {
            throw new IllegalStateException("Validation rule registry is not configured");
        }
        return custom(registry.find(name).orElseThrow(() -> new IllegalArgumentException("Unknown validation rule: " + name)));
    }

    public Map<String, List<String>> errors() {
        Map<String, List<String>> snapshot = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
            snapshot.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        return Map.copyOf(snapshot);
    }

    public Input validate() {
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(formatErrors());
        }
        return input;
    }

    private void addError(String key, String message) {
        errors.computeIfAbsent(key, ignored -> new ArrayList<>()).add(message);
    }

    private String formatErrors() {
        StringBuilder builder = new StringBuilder("Validation failed");
        for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(": ")
                    .append(String.join(", ", entry.getValue()));
        }
        return builder.toString();
    }
}

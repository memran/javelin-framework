package io.javelin.support;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class ValidationRuleRegistry {
    private final Map<String, ValidationRule> rules = new LinkedHashMap<>();

    public void register(ValidationRule rule) {
        Objects.requireNonNull(rule, "rule");
        rules.put(rule.name(), rule);
    }

    public Optional<ValidationRule> find(String name) {
        Objects.requireNonNull(name, "name");
        return Optional.ofNullable(rules.get(name));
    }

    public List<ValidationRule> all() {
        return List.copyOf(new ArrayList<>(rules.values()));
    }
}

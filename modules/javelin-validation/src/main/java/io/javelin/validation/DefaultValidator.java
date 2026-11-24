package io.javelin.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/** Small deterministic validator for the common form/API rule strings. */
public final class DefaultValidator implements Validator {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern INTEGER = Pattern.compile("^-?\\d+$");

    @Override
    public Map<String, List<String>> validate(Map<String, String> values, Map<String, List<String>> rules) {
        Map<String, List<String>> errors = new LinkedHashMap<>();
        rules.forEach((field, fieldRules) -> {
            String value = values.get(field);
            List<String> messages = new ArrayList<>();
            for (String rule : fieldRules) {
                check(field, value, rule, messages);
            }
            if (!messages.isEmpty()) {
                errors.put(field, List.copyOf(messages));
            }
        });
        return Map.copyOf(errors);
    }

    private void check(String field, String value, String rule, List<String> messages) {
        String normalized = rule.trim();
        String name = normalized;
        String argument = "";
        int colon = normalized.indexOf(':');
        if (colon > 0) {
            name = normalized.substring(0, colon).trim();
            argument = normalized.substring(colon + 1).trim();
        }
        switch (name.toLowerCase(Locale.ROOT)) {
            case "required" -> {
                if (value == null || value.isBlank()) {
                    messages.add("is required");
                }
            }
            case "email" -> {
                if (value != null && !value.isBlank() && !EMAIL.matcher(value).matches()) {
                    messages.add("must be a valid email address");
                }
            }
            case "min" -> {
                if (value != null && value.length() < Integer.parseInt(argument)) {
                    messages.add("must be at least " + argument + " characters");
                }
            }
            case "max" -> {
                if (value != null && value.length() > Integer.parseInt(argument)) {
                    messages.add("must be at most " + argument + " characters");
                }
            }
            case "integer" -> {
                if (value != null && !value.isBlank() && !INTEGER.matcher(value).matches()) {
                    messages.add("must be an integer");
                }
            }
            case "oneof" -> {
                if (value != null && !List.of(argument.split(",")).contains(value)) {
                    messages.add("must be one of " + argument);
                }
            }
            case "regex" -> {
                if (value != null && !value.isBlank() && !Pattern.compile(argument).matcher(value).matches()) {
                    messages.add("has an invalid format");
                }
            }
            default -> throw new IllegalArgumentException("Unknown validation rule: " + rule);
        }
    }
}

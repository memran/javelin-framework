package io.javelin.deployment;

/** One production configuration finding. */
public record ValidationIssue(String level, String key, String message) {
    public static ValidationIssue error(String key, String message) {
        return new ValidationIssue("ERROR", key, message);
    }

    public static ValidationIssue warning(String key, String message) {
        return new ValidationIssue("WARN", key, message);
    }

    @Override
    public String toString() {
        return level + " " + key + ": " + message;
    }
}

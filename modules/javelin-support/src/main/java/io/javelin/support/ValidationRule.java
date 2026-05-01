package io.javelin.support;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class ValidationRule {
    public abstract String name();

    public abstract String key();

    protected abstract boolean passes(Input input);

    protected abstract String message();

    public final Optional<String> validate(Input input) {
        Objects.requireNonNull(input, "input");
        return passes(input) ? Optional.empty() : Optional.of(message());
    }

    public final ValidationRule and(ValidationRule other) {
        Objects.requireNonNull(other, "other");
        ValidationRule current = this;
        return of(name() + "+" + other.name(), key(), input -> current.validate(input).isEmpty() && other.validate(input).isEmpty(),
                message() + "; " + other.message());
    }

    public final ValidationRule or(ValidationRule other) {
        Objects.requireNonNull(other, "other");
        ValidationRule current = this;
        return of(name() + "|" + other.name(), key(), input -> current.validate(input).isEmpty() || other.validate(input).isEmpty(),
                message() + "; " + other.message());
    }

    public final ValidationRule negate(String failureMessage) {
        Objects.requireNonNull(failureMessage, "failureMessage");
        ValidationRule current = this;
        return of("not-" + name(), key(), input -> current.validate(input).isPresent(), failureMessage);
    }

    public final ValidationRule named(String newName) {
        Objects.requireNonNull(newName, "newName");
        ValidationRule current = this;
        return of(newName, key(), current::passes, message());
    }

    public final ValidationRule onKey(String newKey) {
        Objects.requireNonNull(newKey, "newKey");
        ValidationRule current = this;
        return of(name(), newKey, current::passes, message());
    }

    public static ValidationRule of(String name, String key, Predicate<Input> predicate, String message) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(message, "message");
        return new ValidationRule() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String key() {
                return key;
            }

            @Override
            protected boolean passes(Input input) {
                return predicate.test(input);
            }

            @Override
            protected String message() {
                return message;
            }
        };
    }
}

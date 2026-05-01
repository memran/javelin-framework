package io.javelin.support;

import java.util.Objects;
import java.util.Optional;

public abstract class ValidationRule {
    public abstract String name();

    public abstract String key();

    protected abstract boolean passes(Input input);

    protected abstract String message();

    public final Optional<String> validate(Input input) {
        Objects.requireNonNull(input, "input");
        return passes(input) ? Optional.empty() : Optional.of(message());
    }
}

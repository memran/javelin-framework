package io.javelin.validation;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Binds the default validator. Register explicitly to replace it with a custom implementation. */
public final class ValidationServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(Validator.class, DefaultValidator::new);
    }
}

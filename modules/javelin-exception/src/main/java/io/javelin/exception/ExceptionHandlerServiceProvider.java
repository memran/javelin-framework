package io.javelin.exception;

import io.javelin.core.Application;
import io.javelin.core.ExceptionHandler;
import io.javelin.core.ServiceProvider;

/** Installs the centralized exception handler. Register explicitly to override the core default. */
public final class ExceptionHandlerServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        ExceptionConfig config = ExceptionConfig.from(app.config(), app.profile());
        SensitiveDataRedactor redactor = config.redact()
                ? (config.redactKeys().isEmpty()
                        ? SensitiveDataRedactor.defaults()
                        : new SensitiveDataRedactor(config.redactKeys()))
                : SensitiveDataRedactor.none();
        app.instance(ExceptionHandler.class, new ConfigurableExceptionHandler(
                config.detailed(), redactor, new FileErrorPageResolver(config.pagesDirectory())));
    }
}

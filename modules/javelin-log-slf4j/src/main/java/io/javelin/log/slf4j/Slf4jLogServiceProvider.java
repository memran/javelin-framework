package io.javelin.log.slf4j;

import io.javelin.core.Application;
import io.javelin.core.Log;
import io.javelin.core.Logger;
import io.javelin.core.ServiceProvider;
import io.javelin.core.StructuredLogger;

/** Installs SLF4J/Logback logging, structured access and config-driven levels. */
public final class Slf4jLogServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        Slf4jLogger logger = new Slf4jLogger();
        app.instance(Logger.class, logger);
        app.instance(StructuredLogger.class, logger);

        LoggingConfig config = LoggingConfig.from(app.config());
        if (config.requestLogging()) {
            app.router().middleware(new RequestLoggingMiddleware(
                    logger, config.requestIdHeader(), config.correlationIdHeader()));
        }
        app.instance(LoggingConfig.class, config);
    }

    @Override
    public void boot(Application app) {
        app.make(LoggingConfig.class).applyLevels();
        Log.use(app.make(Logger.class));
    }
}

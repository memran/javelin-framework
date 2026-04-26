package io.javelin.log.slf4j;

import io.javelin.core.Application;
import io.javelin.core.Log;
import io.javelin.core.Logger;
import io.javelin.core.ServiceProvider;

public final class Slf4jLogServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(Logger.class, Slf4jLogger::new);
    }

    @Override
    public void boot(Application app) {
        Log.use(app.make(Logger.class));
    }
}

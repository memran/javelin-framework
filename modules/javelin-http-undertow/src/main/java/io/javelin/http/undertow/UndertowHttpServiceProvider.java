package io.javelin.http.undertow;

import io.javelin.core.Application;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.ServiceProvider;
import io.javelin.http.common.HttpAdapterConfig;
import io.javelin.http.common.HttpDriverConfig;

import java.nio.file.Path;

public final class UndertowHttpServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String driver = HttpDriverConfig.driver(app.config());
        if (!"undertow".equalsIgnoreCase(driver)) {
            return;
        }
        Path root = app.has(Path.class) ? app.make(Path.class) : Path.of(System.getProperty("user.dir"));
        HttpAdapterConfig config = HttpAdapterConfig.from(app.config(), root);
        app.singleton(HttpKernel.class, () -> new HttpKernel(app.router(), app.make(io.javelin.core.ExceptionHandler.class)));
        app.singleton(HttpServerAdapter.class, () -> new UndertowHttpServer(
                config.host(),
                config.port(),
                app.make(HttpKernel.class),
                config.trustedProxies(),
                config.staticRoot(),
                config.maxRequestBytes(),
                config.staticCachePolicy()
        ));
    }
}

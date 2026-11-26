package io.javelin.http.jdk;

import io.javelin.core.Application;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.ServiceProvider;
import io.javelin.http.common.HttpAdapterConfig;
import io.javelin.http.common.HttpDriverConfig;
import io.javelin.http.common.StaticFallbackConfig;

import java.nio.file.Path;

public final class JdkHttpServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String driver = HttpDriverConfig.driver(app.config());
        if (!"jdk".equalsIgnoreCase(driver)) {
            return;
        }
        Path root = app.has(Path.class) ? app.make(Path.class) : Path.of(System.getProperty("user.dir"));
        HttpAdapterConfig config = HttpAdapterConfig.from(app.config(), root);
        app.singleton(HttpKernel.class, () -> StaticFallbackConfig.apply(
                new HttpKernel(app.router(), app.make(io.javelin.core.ExceptionHandler.class)), app.config()));
        app.singleton(HttpServerAdapter.class, () -> new JdkHttpServer(
                config.host(),
                config.port(),
                app.make(HttpKernel.class),
                config.trustedProxies(),
                config.staticRoot(),
                config.maxRequestBytes(),
                config.staticCachePolicy(),
                config.timeouts()
        ));
    }
}

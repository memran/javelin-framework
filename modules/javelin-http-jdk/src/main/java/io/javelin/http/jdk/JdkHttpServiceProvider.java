package io.javelin.http.jdk;

import io.javelin.core.Application;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpServerAdapter;
import io.javelin.core.ServiceProvider;

public final class JdkHttpServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(HttpKernel.class, () -> new HttpKernel(app.router(), app.make(io.javelin.core.ExceptionHandler.class)));
        app.singleton(HttpServerAdapter.class, () -> new JdkHttpServer(
                app.config().getString("server.host", "127.0.0.1"),
                app.config().getInt("server.port", 8080),
                app.make(HttpKernel.class)
        ));
    }
}

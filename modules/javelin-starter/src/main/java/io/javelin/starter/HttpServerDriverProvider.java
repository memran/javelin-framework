package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.DevProfileMiddleware;
import io.javelin.core.ExceptionHandler;
import io.javelin.core.ServiceProvider;
import io.javelin.http.common.HttpDriverConfig;
import io.javelin.http.jdk.JdkHttpServiceProvider;
import io.javelin.http.netty.NettyHttpServiceProvider;
import io.javelin.http.undertow.UndertowHttpServiceProvider;

/**
 * Wires only the HTTP adapter selected by {@code javelin.starter.http-driver}.
 * Supported values are {@code netty} (default), {@code jdk}, and {@code undertow}.
 * The legacy {@code server.driver} key is used as a fallback.
 */
public final class HttpServerDriverProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        if (app.profile().isDev()) {
            app.instance(ExceptionHandler.class, new DefaultExceptionHandler(true));
            app.router().middleware(new DevProfileMiddleware());
        }
        String driver = HttpDriverConfig.driver(app.config());
        try {
            switch (driver.toLowerCase()) {
                case "netty" -> app.register(new NettyHttpServiceProvider());
                case "undertow" -> app.register(new UndertowHttpServiceProvider());
                case "jdk" -> app.register(new JdkHttpServiceProvider());
                default -> throw new IllegalStateException(
                        "Unknown HTTP driver '" + driver + "'. Expected netty, jdk or undertow.");
            }
        } catch (NoClassDefFoundError error) {
            throw new IllegalStateException(
                    "HTTP driver '" + driver + "' requires the matching adapter module on the classpath", error);
        }
    }
}

package io.javelin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Application {
    private final Container container;
    private final List<ServiceProvider> providers = new ArrayList<>();
    private boolean booted;

    public Application(Config config, Env env) {
        this.container = new Container();
        instance(Application.class, this);
        instance(Container.class, container);
        instance(Config.class, config);
        instance(Env.class, env);
        instance(Router.class, new Router());
        instance(ExceptionHandler.class, new DefaultExceptionHandler());
    }

    public Container container() {
        return container;
    }

    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        container.bind(abstraction, implementation);
    }

    public <T> void singleton(Class<T> type, java.util.function.Supplier<? extends T> factory) {
        container.singleton(type, factory);
    }

    public <T> void instance(Class<T> type, T object) {
        container.instance(type, object);
    }

    public <T> T make(Class<T> type) {
        return container.make(type);
    }

    public boolean has(Class<?> type) {
        return container.has(type);
    }

    public Router router() {
        return make(Router.class);
    }

    public Config config() {
        return make(Config.class);
    }

    public Env env() {
        return make(Env.class);
    }

    public void register(ServiceProvider provider) {
        ensureNotBooted();
        providers.add(Objects.requireNonNull(provider, "provider"));
        provider.register(this);
    }

    public void boot() {
        if (booted) {
            return;
        }
        for (ServiceProvider provider : providers) {
            provider.boot(this);
        }
        booted = true;
    }

    private void ensureNotBooted() {
        if (booted) {
            throw new IllegalStateException("Application has already booted");
        }
    }
}

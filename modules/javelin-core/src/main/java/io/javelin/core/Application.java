package io.javelin.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class Application {
    private final Container container;
    private final List<ServiceProvider> providers = new ArrayList<>();
    private final AppProfile profile;
    private boolean booted;
    private boolean shutdown;

    public Application(Config config, Env env) {
        this.container = new Container();
        this.profile = AppProfile.from(env, config);
        instance(Application.class, this);
        instance(AppProfile.class, profile);
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

    public <T> void singleton(Class<T> type, Supplier<? extends T> factory) {
        container.singleton(type, factory);
    }

    public <T> void factory(Class<T> type, Supplier<? extends T> factory) {
        container.factory(type, factory);
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

    public AppProfile profile() {
        return profile;
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

    /** Registers a batch in dependency order; single-provider registration remains immediate. */
    public void registerProviders(Collection<ServiceProvider> batch) {
        for (ServiceProvider provider : new ProviderSorter().sort(new ArrayList<>(batch))) {
            register(provider);
        }
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

    /** Closes container-owned resources and notifies providers, in reverse registration order. */
    public void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;
        List<Exception> failures = new ArrayList<>();
        List<AutoCloseable> owned = container.closeables();
        for (int index = owned.size() - 1; index >= 0; index--) {
            try {
                owned.get(index).close();
            } catch (Exception exception) {
                failures.add(exception);
            }
        }
        for (int index = providers.size() - 1; index >= 0; index--) {
            try {
                providers.get(index).shutdown(this);
            } catch (RuntimeException exception) {
                failures.add(exception);
            }
        }
        if (!failures.isEmpty()) {
            IllegalStateException error = new IllegalStateException(
                    "Application shutdown reported " + failures.size() + " error(s)");
            failures.forEach(error::addSuppressed);
            throw error;
        }
    }

    private void ensureNotBooted() {
        if (booted) {
            throw new IllegalStateException("Application has already booted");
        }
    }
}

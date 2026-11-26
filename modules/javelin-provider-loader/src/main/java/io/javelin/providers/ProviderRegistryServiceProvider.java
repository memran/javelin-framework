package io.javelin.providers;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Loads enabled providers discovered from manifests. */
public final class ProviderRegistryServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        ProviderRegistry registry = ProviderRegistry.from(app.config());
        app.instance(ProviderRegistry.class, registry);
        registry.register(app);
    }
}

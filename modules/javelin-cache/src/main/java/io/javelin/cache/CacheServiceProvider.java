package io.javelin.cache;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class CacheServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(Cache.class, InMemoryCache::new);
    }
}

package io.javelin.cache.caffeine;

import io.javelin.cache.Cache;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Opt-in provider that replaces the default cache with Caffeine. */
public final class CaffeineCacheServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String driver = app.config().getString("cache.driver", "memory");
        if (!"memory".equalsIgnoreCase(driver) && !"caffeine".equalsIgnoreCase(driver)) {
            return;
        }
        app.singleton(CaffeineCacheConfig.class, () -> CaffeineCacheConfig.from(app.config()));
        app.singleton(CaffeineCache.class, () -> {
            CaffeineCacheConfig config = app.make(CaffeineCacheConfig.class);
            return new CaffeineCache(config.maximumSize(), config.defaultTtl());
        });
        app.instance(Cache.class, app.make(CaffeineCache.class));
    }

    @Override
    public void boot(Application app) {
        if (app.has(CaffeineCacheConfig.class)) {
            io.javelin.caches.Cache.use(app.make(Cache.class));
        }
    }
}

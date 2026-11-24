package io.javelin.cache.valkey;

import io.javelin.cache.Cache;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

/** Binds Valkey as the cache when {@code cache.driver} is {@code valkey}. */
public final class ValkeyCacheServiceProvider implements ServiceProvider {
    public static final String DRIVER = "valkey";
    private static final String DEFAULT_URL = "redis://localhost:6379";

    @Override
    public void register(Application app) {
        String driver = app.config().getString("cache.driver").orElse("memory");
        if (!DRIVER.equalsIgnoreCase(driver)) {
            return;
        }
        String url = app.config().getString("cache.url").orElse(DEFAULT_URL);
        app.singleton(Cache.class, () -> new ValkeyCache(url));
    }
}

package io.javelin.core;

import java.util.List;

public interface ServiceProvider {
    void register(Application app);

    default void boot(Application app) {
    }

    /** Providers listed here are registered before this one. */
    default List<Class<? extends ServiceProvider>> dependsOn() {
        return List.of();
    }

    /** Tie-breaker for independent providers; lower values register first. */
    default int order() {
        return 0;
    }

    default void shutdown(Application app) {
    }
}

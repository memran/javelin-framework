package io.javelin.core;

public interface ServiceProvider {
    void register(Application app);

    default void boot(Application app) {
    }
}

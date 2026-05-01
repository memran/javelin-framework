package io.javelin.core;

public final class ProviderLoader {
    public void load(Application app) {
        for (String className : app.config().getStringList("providers")) {
            app.register(instantiate(className));
        }
    }

    private ServiceProvider instantiate(String className) {
        try {
            Class<?> providerClass = Class.forName(className);
            if (!ServiceProvider.class.isAssignableFrom(providerClass)) {
                throw new IllegalStateException(className + " does not implement ServiceProvider");
            }
            var constructor = providerClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (ServiceProvider) constructor.newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to load provider " + className, exception);
        }
    }
}

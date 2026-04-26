package io.javelin.core;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class Container {
    private final Map<Class<?>, Binding<?>> bindings = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        bindings.put(abstraction, new Binding<>(() -> make(implementation), false));
    }

    public <T> void singleton(Class<T> type, Supplier<? extends T> factory) {
        bindings.put(type, new Binding<>(factory, true));
    }

    public <T> void instance(Class<T> type, T object) {
        instances.put(type, Objects.requireNonNull(object, "object"));
    }

    public boolean has(Class<?> type) {
        return instances.containsKey(type) || bindings.containsKey(type);
    }

    public <T> T make(Class<T> type) {
        Object instance = instances.get(type);
        if (instance != null) {
            return type.cast(instance);
        }

        Binding<?> binding = bindings.get(type);
        if (binding != null) {
            if (binding.singleton()) {
                return type.cast(resolveSingleton(type, binding));
            }
            return type.cast(binding.resolve());
        }

        if (type.isInterface()) {
            throw new IllegalStateException("No binding registered for " + type.getName());
        }
        return construct(type);
    }

    private Object resolveSingleton(Class<?> type, Binding<?> binding) {
        Object instance = instances.get(type);
        if (instance != null) {
            return instance;
        }
        synchronized (instances) {
            instance = instances.get(type);
            if (instance == null) {
                instance = binding.resolve();
                instances.put(type, instance);
            }
            return instance;
        }
    }

    private <T> T construct(Class<T> type) {
        try {
            Constructor<?> constructor = Arrays.stream(type.getDeclaredConstructors())
                    .max(Comparator.comparingInt(Constructor::getParameterCount))
                    .orElseThrow();
            constructor.setAccessible(true);
            Object[] arguments = Arrays.stream(constructor.getParameterTypes()).map(this::make).toArray();
            return type.cast(constructor.newInstance(arguments));
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to resolve " + type.getName(), exception);
        }
    }

    private record Binding<T>(Supplier<? extends T> factory, boolean singleton) {
        Object resolve() {
            return factory.get();
        }
    }
}

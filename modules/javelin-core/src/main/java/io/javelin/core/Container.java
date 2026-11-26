package io.javelin.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Container {
    private static final ThreadLocal<Deque<Class<?>>> RESOLUTION = ThreadLocal.withInitial(ArrayDeque::new);

    private final Map<Class<?>, Binding<?>> bindings = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    private final List<AutoCloseable> closeables = new CopyOnWriteArrayList<>();

    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        factory(abstraction, () -> make(implementation));
    }

    /** Fresh instance per resolution. */
    public <T> void factory(Class<T> type, Supplier<? extends T> factory) {
        bindings.put(type, new Binding<>(Objects.requireNonNull(factory, "factory"), false));
    }

    /** Same instance for every resolution. */
    public <T> void singleton(Class<T> type, Supplier<? extends T> factory) {
        bindings.put(type, new Binding<>(Objects.requireNonNull(factory, "factory"), true));
    }

    public <T> void instance(Class<T> type, T object) {
        Objects.requireNonNull(object, "object");
        instances.put(type, object);
        track(object);
    }

    public boolean has(Class<?> type) {
        return instances.containsKey(type) || bindings.containsKey(type);
    }

    public <T> T make(Class<T> type) {
        Deque<Class<?>> stack = RESOLUTION.get();
        if (stack.contains(type)) {
            throw new IllegalStateException("Circular dependency detected: " + cycle(stack, type));
        }
        stack.push(type);
        try {
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
        } finally {
            stack.pop();
            if (stack.isEmpty()) {
                RESOLUTION.remove();
            }
        }
    }

    /** Objects the container owns and can close on application shutdown. */
    public List<AutoCloseable> closeables() {
        return List.copyOf(closeables);
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
                track(instance);
            }
            return instance;
        }
    }

    private <T> T construct(Class<T> type) {
        try {
            Constructor<?> constructor = Arrays.stream(type.getDeclaredConstructors())
                    .max(Comparator.comparingInt((Constructor<?> c) -> c.getParameterCount())
                            .thenComparing(c -> Arrays.stream(c.getParameterTypes())
                                    .map(Class::getName).collect(Collectors.joining(","))))
                    .orElseThrow();
            constructor.setAccessible(true);
            Object[] arguments = Arrays.stream(constructor.getParameterTypes()).map(this::make).toArray();
            return type.cast(constructor.newInstance(arguments));
        } catch (ReflectiveOperationException | InaccessibleObjectException | SecurityException exception) {
            throw new IllegalStateException("Unable to resolve " + type.getName(), exception);
        }
    }

    private void track(Object object) {
        if (object instanceof AutoCloseable closeable
                && closeables.stream().noneMatch(existing -> existing == closeable)) {
            closeables.add(closeable);
        }
    }

    private static String cycle(Deque<Class<?>> stack, Class<?> type) {
        List<String> names = new ArrayList<>();
        for (Class<?> item : stack) {
            names.add(item.getSimpleName());
        }
        Collections.reverse(names);
        names.add(type.getSimpleName());
        return String.join(" -> ", names);
    }

    private record Binding<T>(Supplier<? extends T> factory, boolean singleton) {
        Object resolve() {
            return factory.get();
        }
    }
}

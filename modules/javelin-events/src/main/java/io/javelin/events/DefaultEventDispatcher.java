package io.javelin.events;

import io.javelin.core.Json;
import io.javelin.queues.Queue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** In-process registry with synchronous listeners and queue-backed listeners. */
public final class DefaultEventDispatcher implements EventDispatcher {
    private final Map<Class<?>, List<EventListener<Object>>> sync = new LinkedHashMap<>();
    private final Map<Class<?>, List<EventListener<Object>>> queued = new LinkedHashMap<>();
    private final Map<String, Class<?>> typesByName = new LinkedHashMap<>();

    @Override
    public <T> void listen(Class<T> eventType, EventListener<T> listener) {
        register(sync, eventType, listener);
    }

    @Override
    public <T> void listenQueued(Class<T> eventType, EventListener<T> listener) {
        register(queued, eventType, listener);
        typesByName.put(eventType.getName(), eventType);
    }

    @Override
    public void dispatch(Object event) {
        Objects.requireNonNull(event, "event");
        Class<?> type = event.getClass();
        for (EventListener<Object> listener : sync.getOrDefault(type, List.of())) {
            invoke(listener, event);
        }
        if (!queued.getOrDefault(type, List.of()).isEmpty()) {
            Queue.dispatch(new QueuedEvent(type.getName(), Json.stringify(event)));
        }
    }

    @Override
    public void dispatchQueued(String eventType, String payload) {
        Class<?> type = typesByName.get(eventType);
        if (type == null) {
            return;
        }
        Object event = Json.parse(payload, type);
        for (EventListener<Object> listener : queued.getOrDefault(type, List.of())) {
            invoke(listener, event);
        }
    }

    @Override
    public List<Class<?>> eventTypes() {
        Set<Class<?>> types = new LinkedHashSet<>(sync.keySet());
        types.addAll(queued.keySet());
        return List.copyOf(types);
    }

    private <T> void register(Map<Class<?>, List<EventListener<Object>>> target,
                              Class<T> eventType, EventListener<T> listener) {
        Objects.requireNonNull(eventType, "eventType");
        Objects.requireNonNull(listener, "listener");
        target.computeIfAbsent(eventType, ignored -> new ArrayList<>()).add(cast(listener));
        typesByName.put(eventType.getName(), eventType);
    }

    private static void invoke(EventListener<Object> listener, Object event) {
        try {
            listener.handle(event);
        } catch (Exception exception) {
            throw new IllegalStateException("Event listener failed for " + event.getClass().getSimpleName(), exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> EventListener<Object> cast(EventListener<T> listener) {
        return (EventListener<Object>) listener;
    }
}

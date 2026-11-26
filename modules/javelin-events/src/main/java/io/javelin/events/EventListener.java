package io.javelin.events;

/** Explicit listener contract for one event type. */
@FunctionalInterface
public interface EventListener<T> {
    void handle(T event) throws Exception;
}

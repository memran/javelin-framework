package io.javelin.observability;

import java.util.function.Supplier;

/** Instrumentation contract for timed operations. */
public interface Tracer {
    <T> T span(String name, Supplier<T> work);

    default void run(String name, Runnable work) {
        span(name, () -> {
            work.run();
            return null;
        });
    }
}

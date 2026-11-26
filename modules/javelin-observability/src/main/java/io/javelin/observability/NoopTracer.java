package io.javelin.observability;

import java.util.function.Supplier;

/** Default tracer: no instrumentation. */
public final class NoopTracer implements Tracer {
    @Override
    public <T> T span(String name, Supplier<T> work) {
        return work.get();
    }
}

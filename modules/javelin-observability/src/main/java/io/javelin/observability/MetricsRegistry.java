package io.javelin.observability;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/** Small thread-safe counter/gauge registry. */
public final class MetricsRegistry {
    private final Map<String, LongAdder> counters = new ConcurrentHashMap<>();
    private final Map<String, AtomicReference<Number>> gauges = new ConcurrentHashMap<>();

    public void increment(String name) {
        increment(name, 1);
    }

    public void increment(String name, long delta) {
        counters.computeIfAbsent(name, ignored -> new LongAdder()).add(delta);
    }

    public long counter(String name) {
        LongAdder counter = counters.get(name);
        return counter == null ? 0 : counter.sum();
    }

    public void gauge(String name, Number value) {
        gauges.computeIfAbsent(name, ignored -> new AtomicReference<>()).set(value);
    }

    public Number gauge(String name) {
        AtomicReference<Number> gauge = gauges.get(name);
        return gauge == null ? null : gauge.get();
    }

    public Map<String, Object> snapshot() {
        Map<String, Object> result = new LinkedHashMap<>();
        counters.forEach((name, counter) -> result.put(name, counter.sum()));
        gauges.forEach((name, gauge) -> result.put(name, gauge.get()));
        return result;
    }
}

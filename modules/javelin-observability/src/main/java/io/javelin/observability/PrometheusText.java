package io.javelin.observability;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Renders the metrics snapshot as a valid Prometheus text exposition. */
final class PrometheusText {
    private PrometheusText() {
    }

    static String render(Map<String, Object> metrics, Set<String> counters,
                         Map<String, Object> runtime, Map<String, Object> startup,
                         Map<String, Object> pools) {
        StringBuilder text = new StringBuilder();
        Set<String> declared = new HashSet<>();
        append(text, "javelin_", metrics, counters, declared);
        append(text, "javelin_runtime_", runtime, Set.of(), declared);
        append(text, "javelin_startup_", startup, Set.of(), declared);
        for (var pool : new TreeMap<>(pools).entrySet()) {
            String label = "{pool=\"" + escape(pool.getKey()) + "\"}";
            new TreeMap<>(map(pool.getValue())).forEach((field, value) -> {
                if (value instanceof Number number) {
                    write(text, "javelin_db_pool_" + sanitize(field), number.toString(),
                            "gauge", label, declared);
                }
            });
        }
        return text.toString();
    }

    private static void append(StringBuilder text, String prefix, Map<String, Object> values,
                               Set<String> counters, Set<String> declared) {
        new TreeMap<>(values).forEach((name, value) -> {
            if (value instanceof Number number) {
                write(text, prefix + sanitize(name), number.toString(),
                        counters.contains(name) ? "counter" : "gauge", "", declared);
            }
        });
    }

    private static void write(StringBuilder text, String name, String value, String type,
                              String labels, Set<String> declared) {
        if (declared.add(name)) {
            text.append("# HELP ").append(name).append(" Javelin ").append(type).append(" metric.\n");
            text.append("# TYPE ").append(name).append(' ').append(type).append('\n');
        }
        text.append(name).append(labels).append(' ').append(value).append('\n');
    }

    private static String sanitize(String name) {
        StringBuilder result = new StringBuilder("javelin_".length() + name.length());
        for (char character : name.toLowerCase(java.util.Locale.ROOT).toCharArray()) {
            boolean valid = (character >= 'a' && character <= 'z')
                    || (character >= '0' && character <= '9') || character == '_';
            result.append(valid ? character : '_');
        }
        return result.toString();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> map(Object value) {
        return value instanceof Map<?, ?> source ? (Map<String, Object>) source : Map.of();
    }
}

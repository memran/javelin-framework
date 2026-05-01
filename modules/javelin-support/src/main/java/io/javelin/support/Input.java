package io.javelin.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public final class Input {
    private final Map<String, String> values;

    private Input(Map<String, String> values) {
        this.values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
    }

    public static Input empty() {
        return new Input(Map.of());
    }

    public static Input from(Map<String, String> values) {
        Objects.requireNonNull(values, "values");
        return new Input(values);
    }

    public Map<String, String> values() {
        return values;
    }

    public Optional<String> raw(String key) {
        Objects.requireNonNull(key, "key");
        return Optional.ofNullable(values.get(key));
    }

    public Optional<String> text(String key) {
        return raw(key)
                .map(Html::stripTags)
                .map(Str::trimToNull);
    }

    public String text(String key, String fallback) {
        return text(key).orElse(fallback);
    }

    public Optional<String> filename(String key) {
        return raw(key)
                .map(Security::sanitizeFilename);
    }

    public String filename(String key, String fallback) {
        return filename(key).orElse(fallback);
    }

    public Optional<String> file(String key) {
        return filename(key);
    }

    public String file(String key, String fallback) {
        return filename(key, fallback);
    }

    public Collection<String> array(String key) {
        return raw(key)
                .map(value -> {
                    String stripped = Html.stripTags(value);
                    List<String> items = new ArrayList<>();
                    for (String part : stripped.split(",")) {
                        String item = Str.trimToNull(part);
                        if (Str.isNotBlank(item)) {
                            items.add(item);
                        }
                    }
                    return Collection.from(items);
                })
                .orElse(Collection.empty());
    }

    public Optional<Integer> integer(String key) {
        return raw(key)
                .map(Str::trimToNull)
                .filter(Str::isNotBlank)
                .map(value -> {
                    try {
                        return Integer.valueOf(value);
                    } catch (NumberFormatException exception) {
                        return null;
                    }
                });
    }

    public int integer(String key, int fallback) {
        return integer(key).orElse(fallback);
    }

    public Optional<Boolean> bool(String key) {
        return raw(key)
                .map(Str::trimToNull)
                .filter(Str::isNotBlank)
                .map(value -> switch (value.toLowerCase(java.util.Locale.ROOT)) {
                    case "1", "true", "yes", "on" -> Boolean.TRUE;
                    case "0", "false", "no", "off" -> Boolean.FALSE;
                    default -> null;
                });
    }

    public boolean bool(String key, boolean fallback) {
        return bool(key).orElse(fallback);
    }

    public Input merge(Map<String, String> additional) {
        Objects.requireNonNull(additional, "additional");
        if (additional.isEmpty()) {
            return this;
        }
        Map<String, String> merged = new LinkedHashMap<>(values);
        merged.putAll(additional);
        return new Input(merged);
    }

    public Input only(String... keys) {
        Objects.requireNonNull(keys, "keys");
        if (keys.length == 0) {
            return empty();
        }
        Map<String, String> selected = new LinkedHashMap<>();
        for (String key : keys) {
            if (key != null && values.containsKey(key)) {
                selected.put(key, values.get(key));
            }
        }
        return new Input(selected);
    }

    public Input except(String... keys) {
        Objects.requireNonNull(keys, "keys");
        if (keys.length == 0) {
            return this;
        }
        java.util.Set<String> excluded = new java.util.HashSet<>();
        for (String key : keys) {
            if (key != null) {
                excluded.add(key);
            }
        }
        Map<String, String> selected = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (!excluded.contains(entry.getKey())) {
                selected.put(entry.getKey(), entry.getValue());
            }
        }
        return new Input(selected);
    }

    public Input tap(Consumer<? super Input> callback) {
        Objects.requireNonNull(callback, "callback");
        callback.accept(this);
        return this;
    }

    public Validation validate() {
        return Validation.of(this);
    }

    public Validation validate(ValidationRuleRegistry registry) {
        return Validation.of(this, registry);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}

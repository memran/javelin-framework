package io.javelin.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class View {
    private static volatile ViewRenderer renderer = (template, data) -> new HtmlResponse("");

    private View() {
    }

    public static void use(ViewRenderer renderer) {
        View.renderer = renderer;
    }

    public static HtmlResponse render(String template, Map<String, Object> data) {
        return view(template, data);
    }

    public static HtmlResponse view(String template) {
        return view(template, Map.of());
    }

    public static HtmlResponse view(String template, Map<String, Object> data) {
        Objects.requireNonNull(template, "template");
        Objects.requireNonNull(data, "data");
        return renderer.render(template, data);
    }

    public static HtmlResponse view(String template, Object... variables) {
        Objects.requireNonNull(template, "template");
        Objects.requireNonNull(variables, "variables");
        if (variables.length == 0) {
            return view(template);
        }
        if (variables.length % 2 != 0) {
            throw new IllegalArgumentException("variables must contain an even number of key/value entries");
        }

        Map<String, Object> data = new LinkedHashMap<>();
        for (int index = 0; index < variables.length; index += 2) {
            Object key = variables[index];
            if (!(key instanceof String name)) {
                throw new IllegalArgumentException("variable keys must be strings");
            }
            data.put(name, variables[index + 1]);
        }
        return view(template, data);
    }
}

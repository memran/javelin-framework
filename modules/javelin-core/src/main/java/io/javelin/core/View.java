package io.javelin.core;

import java.util.Map;

public final class View {
    private static volatile ViewRenderer renderer = (template, data) -> new HtmlResponse("");

    private View() {
    }

    public static void use(ViewRenderer renderer) {
        View.renderer = renderer;
    }

    public static HtmlResponse render(String template, Map<String, Object> data) {
        return renderer.render(template, data);
    }
}

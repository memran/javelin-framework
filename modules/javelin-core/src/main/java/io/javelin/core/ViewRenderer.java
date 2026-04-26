package io.javelin.core;

import java.util.Map;

public interface ViewRenderer {
    HtmlResponse render(String template, Map<String, Object> data);
}

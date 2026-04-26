package io.javelin.core;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HtmlResponse extends Response {
    public HtmlResponse(String html) {
        this(html, 200);
    }

    public HtmlResponse(String html, int status) {
        super(status, Map.of("Content-Type", "text/html; charset=utf-8"), html.getBytes(StandardCharsets.UTF_8));
    }
}

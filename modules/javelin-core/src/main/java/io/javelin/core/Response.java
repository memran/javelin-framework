package io.javelin.core;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    private final int status;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(int status, Map<String, String> headers, byte[] body) {
        this.status = status;
        this.headers = new LinkedHashMap<>(headers);
        this.body = body == null ? new byte[0] : body.clone();
    }

    public static Response text(String body) {
        return new Response(200, Map.of("Content-Type", "text/plain; charset=utf-8"), body.getBytes(StandardCharsets.UTF_8));
    }

    public int status() {
        return status;
    }

    public Map<String, String> headers() {
        return Map.copyOf(headers);
    }

    public byte[] body() {
        return body.clone();
    }
}

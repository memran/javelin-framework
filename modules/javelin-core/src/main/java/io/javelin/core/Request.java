package io.javelin.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javelin.support.Input;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Request {
    private static final ObjectMapper JSON = new ObjectMapper();

    private final HttpMethod method;
    private final String path;
    private final Map<String, List<String>> headers;
    private final Map<String, String> query;
    private final Map<String, String> params;
    private final byte[] body;
    private final String remoteAddress;

    public Request(HttpMethod method, String path, Map<String, List<String>> headers, Map<String, String> query,
                   Map<String, String> params, byte[] body, String remoteAddress) {
        this.method = method;
        this.path = path;
        this.headers = new LinkedHashMap<>(headers);
        this.query = new LinkedHashMap<>(query);
        this.params = new LinkedHashMap<>(params);
        this.body = body == null ? new byte[0] : body.clone();
        this.remoteAddress = remoteAddress;
    }

    public static Map<String, String> parseQuery(String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return Collections.emptyMap();
        }
        Map<String, String> values = new LinkedHashMap<>();
        for (String pair : rawQuery.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length == 2 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            values.put(key, value);
        }
        return values;
    }

    public HttpMethod method() {
        return method;
    }

    public String path() {
        return path;
    }

    public Optional<String> header(String name) {
        return headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst();
    }

    public Optional<String> query(String name) {
        return Optional.ofNullable(query.get(name));
    }

    public Optional<String> param(String name) {
        return Optional.ofNullable(params.get(name));
    }

    public byte[] body() {
        return body.clone();
    }

    public String bodyAsString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    public Input input() {
        Map<String, String> values = new LinkedHashMap<>(query);
        if (isFormUrlEncoded()) {
            values.putAll(parseQuery(bodyAsString()));
        }
        return Input.from(values);
    }

    public <T> T json(Class<T> type) {
        try {
            return JSON.readValue(body, type);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid JSON request body", exception);
        }
    }

    public String remoteAddress() {
        return remoteAddress;
    }

    public Map<String, String> queryParameters() {
        return Map.copyOf(query);
    }

    Request withParams(Map<String, String> routeParams) {
        return new Request(method, path, headers, query, routeParams, body, remoteAddress);
    }

    private boolean isFormUrlEncoded() {
        return header("Content-Type")
                .map(value -> value.toLowerCase().contains("application/x-www-form-urlencoded"))
                .orElse(false);
    }
}

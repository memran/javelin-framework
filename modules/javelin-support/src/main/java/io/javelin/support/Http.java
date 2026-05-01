package io.javelin.support;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class Http {
    private Http() {
    }

    public static Handle client() {
        return new Handle(null, HttpClient.newHttpClient(), Map.of(), null);
    }

    public static Handle of(String baseUrl) {
        return client().baseUrl(baseUrl);
    }

    public static Handle of(URI baseUrl) {
        return client().baseUrl(baseUrl);
    }

    public static final class Handle {
        private final URI baseUrl;
        private final HttpClient client;
        private final Map<String, String> headers;
        private final Duration timeout;

        private Handle(URI baseUrl, HttpClient client, Map<String, String> headers, Duration timeout) {
            this.baseUrl = baseUrl;
            this.client = Objects.requireNonNull(client, "client");
            this.headers = Map.copyOf(headers);
            this.timeout = timeout;
        }

        public Handle baseUrl(String baseUrl) {
            return baseUrl(baseUrl == null ? null : URI.create(baseUrl));
        }

        public Handle baseUrl(URI baseUrl) {
            return new Handle(normalizeBaseUrl(baseUrl), client, headers, timeout);
        }

        public Handle timeout(Duration timeout) {
            return new Handle(baseUrl, client, headers, Objects.requireNonNull(timeout, "timeout"));
        }

        public Handle withHeader(String name, String value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            Map<String, String> next = new LinkedHashMap<>(headers);
            next.put(name, value);
            return new Handle(baseUrl, client, next, timeout);
        }

        public Handle withHeaders(Map<String, String> values) {
            Objects.requireNonNull(values, "values");
            Map<String, String> next = new LinkedHashMap<>(headers);
            values.forEach((name, value) -> {
                if (name != null && value != null) {
                    next.put(name, value);
                }
            });
            return new Handle(baseUrl, client, next, timeout);
        }

        public Handle when(boolean condition, UnaryOperator<Handle> callback) {
            Objects.requireNonNull(callback, "callback");
            return condition ? Objects.requireNonNull(callback.apply(this), "callback result") : this;
        }

        public Handle unless(boolean condition, UnaryOperator<Handle> callback) {
            return when(!condition, callback);
        }

        public Response get(String path) {
            return send("GET", path, null, null);
        }

        public Response getJson(String path) {
            return withHeader("Accept", "application/json").get(path);
        }

        public Response head(String path) {
            return send("HEAD", path, null, null);
        }

        public Response delete(String path) {
            return send("DELETE", path, null, null);
        }

        public Response postJson(String path, String json) {
            return send("POST", path, json, "application/json");
        }

        public Response putJson(String path, String json) {
            return send("PUT", path, json, "application/json");
        }

        public Response patchJson(String path, String json) {
            return send("PATCH", path, json, "application/json");
        }

        public Response postText(String path, String text) {
            return send("POST", path, text, "text/plain; charset=utf-8");
        }

        public Response postForm(String path, Map<String, String> form) {
            return send("POST", path, formEncode(form), "application/x-www-form-urlencoded; charset=utf-8");
        }

        public Path download(String path, Path target) {
            Objects.requireNonNull(target, "target");
            Response response = get(path);
            try {
                File.ensureParentDirectory(target);
                Files.write(target, response.body());
                return target;
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to save download to " + target, exception);
            }
        }

        public Response send(String method, String path, String body, String contentType) {
            Objects.requireNonNull(method, "method");
            Objects.requireNonNull(path, "path");
            try {
                HttpRequest.Builder builder = HttpRequest.newBuilder(resolve(path));
                if (timeout != null) {
                    builder.timeout(timeout);
                }
                headers.forEach(builder::header);
                if (contentType != null) {
                    builder.header("Content-Type", contentType);
                }
                if (body == null) {
                    builder.method(method, HttpRequest.BodyPublishers.noBody());
                } else {
                    builder.method(method, HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
                }
                HttpResponse<byte[]> response = client.send(builder.build(), BodyHandlers.ofByteArray());
                return new Response(response.statusCode(), response.headers().map(), response.body());
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("HTTP request interrupted", exception);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to execute HTTP request", exception);
            }
        }

        private URI resolve(String path) {
            URI uri = URI.create(path);
            if (uri.isAbsolute() || baseUrl == null) {
                if (!uri.isAbsolute() && baseUrl == null) {
                    throw new IllegalStateException("baseUrl is required for relative request paths");
                }
                return uri;
            }
            String base = baseUrl.toString();
            if (!base.endsWith("/") && !path.startsWith("/")) {
                base += "/";
            }
            return URI.create(base + path);
        }

        private static URI normalizeBaseUrl(URI baseUrl) {
            if (baseUrl == null) {
                return null;
            }
            if (baseUrl.getScheme() == null || baseUrl.getHost() == null) {
                throw new IllegalArgumentException("baseUrl must be absolute");
            }
            return baseUrl;
        }

        private static String formEncode(Map<String, String> form) {
            Objects.requireNonNull(form, "form");
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : form.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                if (!builder.isEmpty()) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                builder.append('=');
                builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            }
            return builder.toString();
        }
    }

    public record Response(int status, Map<String, List<String>> headers, byte[] body) {
        public Response {
            headers = Map.copyOf(headers);
            body = body == null ? new byte[0] : body.clone();
        }

        public boolean successful() {
            return status >= 200 && status < 300;
        }

        public Optional<String> header(String name) {
            Objects.requireNonNull(name, "name");
            return headers.entrySet().stream()
                    .filter(entry -> entry.getKey() != null && entry.getKey().equalsIgnoreCase(name))
                    .map(Map.Entry::getValue)
                    .filter(values -> !values.isEmpty())
                    .map(values -> values.get(0))
                    .findFirst();
        }

        public Optional<String> contentType() {
            return header("Content-Type");
        }

        public String bodyText() {
            return new String(body, StandardCharsets.UTF_8);
        }
    }
}

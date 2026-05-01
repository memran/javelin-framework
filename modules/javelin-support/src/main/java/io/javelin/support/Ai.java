package io.javelin.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public final class Ai {
    private static final ObjectMapper JSON = new ObjectMapper();

    private Ai() {
    }

    public static Handle client() {
        return new Handle(HttpClient.newHttpClient(), null, Map.of(), null, "/v1/chat/completions", null, null, null, null);
    }

    public static Handle of(String baseUrl) {
        return client().baseUrl(baseUrl);
    }

    public static Handle of(URI baseUrl) {
        return client().baseUrl(baseUrl);
    }

    public static Handle from(Path configFile) {
        Objects.requireNonNull(configFile, "configFile");
        Properties properties = new Properties();
        if (Files.exists(configFile)) {
            try (InputStream stream = Files.newInputStream(configFile)) {
                properties.load(stream);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to read AI config: " + configFile, exception);
            }
        }
        Handle handle = client();
        String provider = properties.getProperty("provider");
        if (provider != null && !provider.isBlank()) {
            handle = handle.provider(provider);
        }
        String baseUrl = properties.getProperty("base_url");
        if (baseUrl != null && !baseUrl.isBlank()) {
            handle = handle.baseUrl(baseUrl);
        }
        String chatPath = properties.getProperty("chat_path");
        if (chatPath != null && !chatPath.isBlank()) {
            handle = handle.chatPath(chatPath);
        }
        String model = properties.getProperty("model");
        if (model != null && !model.isBlank()) {
            handle = handle.model(model);
        }
        String apiKey = properties.getProperty("api_key");
        if (apiKey != null && !apiKey.isBlank()) {
            handle = handle.apiKey(apiKey);
        }
        String system = properties.getProperty("system");
        if (system != null && !system.isBlank()) {
            handle = handle.system(system);
        }
        String temperature = properties.getProperty("temperature");
        if (temperature != null && !temperature.isBlank()) {
            handle = handle.temperature(Double.parseDouble(temperature));
        }
        String timeout = properties.getProperty("timeout_seconds");
        if (timeout != null && !timeout.isBlank()) {
            handle = handle.timeout(Duration.ofSeconds(Long.parseLong(timeout)));
        }
        return handle;
    }

    public static final class Handle {
        private final HttpClient client;
        private final URI baseUrl;
        private final Map<String, String> headers;
        private final Duration timeout;
        private final String chatPath;
        private final String provider;
        private final String model;
        private final String system;
        private final Double temperature;

        private Handle(HttpClient client, URI baseUrl, Map<String, String> headers, Duration timeout, String chatPath, String provider, String model, String system, Double temperature) {
            this.client = Objects.requireNonNull(client, "client");
            this.baseUrl = baseUrl;
            this.headers = Map.copyOf(headers);
            this.timeout = timeout;
            this.chatPath = Objects.requireNonNull(chatPath, "chatPath");
            this.provider = provider;
            this.model = model;
            this.system = system;
            this.temperature = temperature;
        }

        public Handle baseUrl(String baseUrl) {
            return baseUrl(baseUrl == null ? null : URI.create(baseUrl));
        }

        public Handle baseUrl(URI baseUrl) {
            return new Handle(client, normalizeBaseUrl(baseUrl), headers, timeout, chatPath, provider, model, system, temperature);
        }

        public Handle chatPath(String chatPath) {
            Objects.requireNonNull(chatPath, "chatPath");
            return new Handle(client, baseUrl, headers, timeout, chatPath, provider, model, system, temperature);
        }

        public Handle provider(String provider) {
            return new Handle(client, baseUrl, headers, timeout, chatPath, normalize(provider), model, system, temperature);
        }

        public Handle model(String model) {
            return new Handle(client, baseUrl, headers, timeout, chatPath, provider, normalize(model), system, temperature);
        }

        public Handle system(String system) {
            return new Handle(client, baseUrl, headers, timeout, chatPath, provider, model, normalize(system), temperature);
        }

        public Handle apiKey(String apiKey) {
            String normalized = normalize(apiKey);
            if (normalized == null) {
                return new Handle(client, baseUrl, headers, timeout, chatPath, provider, model, system, temperature);
            }
            return withHeader("Authorization", "Bearer " + normalized);
        }

        public Handle temperature(double temperature) {
            return new Handle(client, baseUrl, headers, timeout, chatPath, provider, model, system, temperature);
        }

        public Handle timeout(Duration timeout) {
            return new Handle(client, baseUrl, headers, Objects.requireNonNull(timeout, "timeout"), chatPath, provider, model, system, temperature);
        }

        public Handle withHeader(String name, String value) {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
            Map<String, String> next = new LinkedHashMap<>(headers);
            next.put(name, value);
            return new Handle(client, baseUrl, next, timeout, chatPath, provider, model, system, temperature);
        }

        public Handle withHeaders(Map<String, String> values) {
            Objects.requireNonNull(values, "values");
            Map<String, String> next = new LinkedHashMap<>(headers);
            values.forEach((name, value) -> {
                if (name != null && value != null) {
                    next.put(name, value);
                }
            });
            return new Handle(client, baseUrl, next, timeout, chatPath, provider, model, system, temperature);
        }

        public Handle when(boolean condition, UnaryOperator<Handle> callback) {
            Objects.requireNonNull(callback, "callback");
            return condition ? Objects.requireNonNull(callback.apply(this), "callback result") : this;
        }

        public Handle unless(boolean condition, UnaryOperator<Handle> callback) {
            return when(!condition, callback);
        }

        public Reply chat(String prompt) {
            Objects.requireNonNull(prompt, "prompt");
            return chat(List.of(Message.user(prompt)));
        }

        public Reply chat(List<Message> messages) {
            return send(messages, false, null);
        }

        public Reply stream(String prompt, Consumer<String> onToken) {
            Objects.requireNonNull(prompt, "prompt");
            return stream(List.of(Message.user(prompt)), onToken);
        }

        public Reply stream(List<Message> messages, Consumer<String> onToken) {
            return send(messages, true, onToken);
        }

        public Optional<String> provider() {
            return Optional.ofNullable(provider);
        }

        public Optional<String> model() {
            return Optional.ofNullable(model);
        }

        public Optional<String> system() {
            return Optional.ofNullable(system);
        }

        private Reply send(List<Message> messages, boolean stream, Consumer<String> onToken) {
            Objects.requireNonNull(messages, "messages");
            if (messages.isEmpty()) {
                throw new IllegalArgumentException("messages must not be empty");
            }
            ensureConfigured();
            List<Map<String, Object>> payloadMessages = new ArrayList<>();
            if (system != null && !system.isBlank()) {
                payloadMessages.add(Map.of("role", "system", "content", system));
            }
            for (Message message : messages) {
                payloadMessages.add(Map.of("role", message.role(), "content", message.content()));
            }

            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("model", model);
            payload.put("messages", payloadMessages);
            payload.put("stream", stream);
            if (temperature != null) {
                payload.put("temperature", temperature);
            }

            String requestJson = writeJson(payload);
            HttpRequest.Builder builder = HttpRequest.newBuilder(resolve(chatPath));
            if (timeout != null) {
                builder.timeout(timeout);
            }
            headers.forEach(builder::header);
            builder.header("Content-Type", "application/json");
            builder.header("Accept", stream ? "text/event-stream" : "application/json");
            builder.POST(HttpRequest.BodyPublishers.ofString(requestJson, StandardCharsets.UTF_8));

            if (stream) {
                return streamResponse(builder, onToken);
            }
            return chatResponse(builder);
        }

        private Reply chatResponse(HttpRequest.Builder builder) {
            try {
                HttpResponse<String> response = client.send(builder.build(), BodyHandlers.ofString(StandardCharsets.UTF_8));
                JsonNode root = JSON.readTree(response.body());
                String text = extractText(root);
                return new Reply(provider, model, text, response.body(), false);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("AI request interrupted", exception);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to execute AI request", exception);
            }
        }

        private Reply streamResponse(HttpRequest.Builder builder, Consumer<String> onToken) {
            try {
                HttpResponse<java.util.stream.Stream<String>> response = client.send(builder.build(), BodyHandlers.ofLines());
                StringBuilder content = new StringBuilder();
                StringBuilder raw = new StringBuilder();
                try (var lines = response.body()) {
                    lines.forEach(line -> {
                        if (raw.length() > 0) {
                            raw.append(System.lineSeparator());
                        }
                        raw.append(line);
                        String token = extractStreamToken(line);
                        if (token != null && !token.isEmpty()) {
                            content.append(token);
                            if (onToken != null) {
                                onToken.accept(token);
                            }
                        }
                    });
                }
                return new Reply(provider, model, content.toString(), raw.toString(), true);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("AI stream interrupted", exception);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to execute AI stream", exception);
            }
        }

        private void ensureConfigured() {
            if (baseUrl == null) {
                throw new IllegalStateException("baseUrl is required");
            }
            if (model == null || model.isBlank()) {
                throw new IllegalStateException("model is required");
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

        private static String normalize(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }

        private static String writeJson(Map<String, Object> payload) {
            try {
                return JSON.writeValueAsString(payload);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to encode AI payload", exception);
            }
        }

        private static String extractText(JsonNode root) {
            String text = textAt(root, "choices", 0, "message", "content");
            if (text != null) {
                return text;
            }
            text = textAt(root, "choices", 0, "delta", "content");
            if (text != null) {
                return text;
            }
            text = textAt(root, "choices", 0, "text");
            if (text != null) {
                return text;
            }
            return root.path("content").asText("");
        }

        private static String extractStreamToken(String line) {
            if (Str.isBlank(line)) {
                return null;
            }
            String trimmed = line.trim();
            if (!trimmed.startsWith("data:")) {
                return null;
            }
            String data = trimmed.substring(5).trim();
            if ("[DONE]".equals(data)) {
                return null;
            }
            try {
                return extractText(JSON.readTree(data));
            } catch (IOException exception) {
                return data;
            }
        }

        private static String textAt(JsonNode root, Object... path) {
            JsonNode current = root;
            for (Object segment : path) {
                if (segment instanceof String name) {
                    current = current.path(name);
                } else if (segment instanceof Integer index) {
                    current = current.path(index);
                }
            }
            String value = current.asText(null);
            return value == null || value.isBlank() ? null : value;
        }
    }

    public record Message(String role, String content) {
        public Message {
            Objects.requireNonNull(role, "role");
            Objects.requireNonNull(content, "content");
        }

        public static Message system(String content) {
            return new Message("system", content);
        }

        public static Message user(String content) {
            return new Message("user", content);
        }

        public static Message assistant(String content) {
            return new Message("assistant", content);
        }
    }

    public record Reply(String provider, String model, String text, String rawPayload, boolean streamed) {
        public Reply {
            text = text == null ? "" : text;
            rawPayload = rawPayload == null ? "" : rawPayload;
        }
    }
}

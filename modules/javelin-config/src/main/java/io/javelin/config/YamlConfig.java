package io.javelin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.javelin.core.Config;
import io.javelin.core.Env;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class YamlConfig implements Config {
    private static final ObjectMapper YAML = new ObjectMapper(new YAMLFactory());

    private final Map<String, Object> values;
    private final Env env;

    private YamlConfig(Map<String, Object> values, Env env) {
        this.values = values;
        this.env = env;
    }

    public static YamlConfig load(Path path, Env env) {
        if (!Files.exists(path)) {
            String resource = path.toString().replace('\\', '/');
            int configIndex = resource.indexOf("config/");
            if (configIndex >= 0) {
                resource = resource.substring(configIndex);
            }
            try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
                if (stream == null) {
                    return new YamlConfig(Collections.emptyMap(), env);
                }
                Map<String, Object> values = YAML.readValue(stream, new TypeReference<>() {
                });
                return new YamlConfig(values == null ? Collections.emptyMap() : values, env);
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to load YAML config resource " + resource, exception);
            }
        }
        try {
            Map<String, Object> values = YAML.readValue(Files.readString(path), new TypeReference<>() {
            });
            return new YamlConfig(values == null ? Collections.emptyMap() : values, env);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load YAML config from " + path, exception);
        }
    }

    public static YamlConfig loadDirectory(Path directory, Env env) {
        Map<String, Object> values = new LinkedHashMap<>();
        Path app = directory.resolve("app.yaml");
        merge(values, load(app, env).values);

        if (!Files.isDirectory(directory)) {
            return new YamlConfig(values, env);
        }

        try (Stream<Path> files = Files.list(directory)) {
            files
                    .filter(Files::isRegularFile)
                    .filter(YamlConfig::isYaml)
                    .filter(path -> !path.getFileName().toString().equals("app.yaml"))
                    .sorted()
                    .forEach(path -> {
                        String namespace = namespace(path);
                        Map<String, Object> namespaced = new LinkedHashMap<>();
                        namespaced.put(namespace, load(path, env).values);
                        merge(values, namespaced);
                    });
            return new YamlConfig(values, env);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load YAML config directory " + directory, exception);
        }
    }

    @Override
    public Optional<String> getString(String key) {
        return value(key).map(Object::toString).map(this::resolveEnv);
    }

    @Override
    public Optional<Integer> getInt(String key) {
        return getString(key).map(Integer::parseInt);
    }

    @Override
    public Optional<Boolean> getBoolean(String key) {
        return getString(key).map(Boolean::parseBoolean);
    }

    @Override
    public List<String> getStringList(String key) {
        return value(key)
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .orElse(List.of())
                .stream()
                .map(item -> resolveEnv(item.toString()))
                .toList();
    }

    private Optional<Object> value(String key) {
        Object current = values;
        for (String part : key.split("\\.")) {
            if (!(current instanceof Map<?, ?> map) || !map.containsKey(part)) {
                return Optional.empty();
            }
            current = map.get(part);
        }
        return Optional.ofNullable(current);
    }

    private String resolveEnv(String value) {
        if (value.startsWith("${") && value.endsWith("}")) {
            String expression = value.substring(2, value.length() - 1);
            String[] parts = expression.split(":", 2);
            return env.get(parts[0], parts.length == 2 ? parts[1] : "");
        }
        return value;
    }

    private static boolean isYaml(Path path) {
        String name = path.getFileName().toString();
        return name.endsWith(".yaml") || name.endsWith(".yml");
    }

    private static String namespace(Path path) {
        String name = path.getFileName().toString();
        int dot = name.lastIndexOf('.');
        return dot > 0 ? name.substring(0, dot) : name;
    }

    @SuppressWarnings("unchecked")
    private static void merge(Map<String, Object> target, Map<String, Object> source) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            Object current = target.get(entry.getKey());
            Object incoming = entry.getValue();
            if (current instanceof Map<?, ?> currentMap && incoming instanceof Map<?, ?> incomingMap) {
                merge((Map<String, Object>) currentMap, (Map<String, Object>) incomingMap);
            } else {
                target.put(entry.getKey(), incoming);
            }
        }
    }
}

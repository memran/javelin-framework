package io.javelin.providers;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.ServiceProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Discovers manifests, validates them and registers enabled providers in dependency order. */
public final class ProviderRegistry {
    private final Path directory;
    private final List<String> enabled;
    private final List<String> disabled;
    private final boolean strict;
    private final Map<String, ProviderManifest> manifests = new LinkedHashMap<>();
    private final List<ProviderConflict> conflicts = new ArrayList<>();

    public ProviderRegistry(Path directory, List<String> enabled, List<String> disabled, boolean strict) {
        this.directory = directory;
        this.enabled = List.copyOf(enabled);
        this.disabled = List.copyOf(disabled);
        this.strict = strict;
        load();
    }

    public static ProviderRegistry from(Config config) {
        return new ProviderRegistry(
                Path.of(config.getString("provider.directory", "modules")),
                config.getStringList("provider.enabled"),
                config.getStringList("provider.disabled"),
                config.getBoolean("provider.strict").orElse(false));
    }

    private void load() {
        if (Files.isDirectory(directory)) {
            try (var paths = Files.list(directory)) {
                paths.filter(Files::isDirectory)
                        .map(path -> path.resolve(ProviderManifest.FILE))
                        .filter(Files::isRegularFile)
                        .sorted()
                        .forEach(path -> add(read(path)));
            } catch (IOException exception) {
                conflicts.add(new ProviderConflict("local", "Unable to read " + directory + ": " + exception.getMessage()));
            }
        }
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader()
                    .getResources(ProviderManifest.RESOURCE);
            while (resources.hasMoreElements()) {
                try (InputStream stream = resources.nextElement().openStream()) {
                    add(ProviderManifest.fromJson(new String(stream.readAllBytes(), StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException exception) {
            conflicts.add(new ProviderConflict("classpath", "Unable to read manifests: " + exception.getMessage()));
        }
        validate();
    }

    private static ProviderManifest read(Path path) {
        try {
            return ProviderManifest.fromJson(Files.readString(path, StandardCharsets.UTF_8));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read provider manifest " + path, exception);
        }
    }

    private void add(ProviderManifest manifest) {
        if (manifest.name().isBlank() || manifest.providerClass().isBlank()) {
            conflicts.add(new ProviderConflict(manifest.name(), "Manifest is missing name or providerClass"));
            return;
        }
        ProviderManifest existing = manifests.putIfAbsent(manifest.name(), manifest);
        if (existing != null) {
            conflicts.add(new ProviderConflict(manifest.name(), "Duplicate provider name: " + existing.version()
                    + " and " + manifest.version()));
        }
    }

    private void validate() {
        for (ProviderManifest manifest : manifests.values()) {
            if (!ProviderVersion.compatible(JavelinVersion.version(), manifest.minJavelin(), manifest.maxJavelin())) {
                conflicts.add(new ProviderConflict(manifest.name(), "Incompatible with Javelin " + JavelinVersion.version()
                        + " (requires " + manifest.minJavelin() + " - " + manifest.maxJavelin() + ")"));
            }
            for (String dependency : manifest.dependencies()) {
                if (!manifests.containsKey(dependency)) {
                    conflicts.add(new ProviderConflict(manifest.name(), "Missing dependency: " + dependency));
                }
            }
        }
        order(names(manifests.values()));
    }

    public List<ProviderManifest> manifests() {
        return List.copyOf(manifests.values());
    }

    public List<ProviderManifest> enabled() {
        List<ProviderManifest> result = new ArrayList<>();
        for (ProviderManifest manifest : manifests.values()) {
            if (!manifest.enabled()) continue;
            if (!enabled.isEmpty() && !enabled.contains(manifest.name())) continue;
            if (disabled.contains(manifest.name())) continue;
            result.add(manifest);
        }
        return List.copyOf(result);
    }

    public List<ProviderConflict> conflicts() {
        return List.copyOf(conflicts);
    }

    /** Instantiates enabled providers in dependency order. */
    public List<ServiceProvider> providers() {
        List<ProviderManifest> ordered = enabled().stream()
                .filter(manifest -> conflicts.stream().noneMatch(conflict ->
                        conflict.provider().equals(manifest.name())))
                .toList();
        Map<String, ProviderManifest> byName = new LinkedHashMap<>();
        ordered.forEach(manifest -> byName.put(manifest.name(), manifest));
        List<ServiceProvider> providers = new ArrayList<>();
        for (String name : order(byName.keySet())) {
            ProviderManifest manifest = byName.get(name);
            if (manifest == null) continue;
            try {
                Class<?> type = Class.forName(manifest.providerClass());
                if (!ServiceProvider.class.isAssignableFrom(type)) {
                    conflicts.add(new ProviderConflict(name, "Does not implement ServiceProvider"));
                    continue;
                }
                providers.add((ServiceProvider) type.getDeclaredConstructor().newInstance());
            } catch (ReflectiveOperationException exception) {
                conflicts.add(new ProviderConflict(name, "Unable to instantiate: " + exception.getMessage()));
            }
        }
        if (strict && !conflicts.isEmpty()) {
            throw new IllegalStateException("Provider conflicts: " + conflicts);
        }
        return List.copyOf(providers);
    }

    public void register(Application app) {
        app.registerProviders(providers());
    }

    private List<String> order(Set<String> names) {
        Set<String> visited = new LinkedHashSet<>();
        Set<String> visiting = new LinkedHashSet<>();
        List<String> result = new ArrayList<>();
        for (String name : names) {
            visit(name, names, visited, visiting, result);
        }
        return result;
    }

    private void visit(String name, Set<String> names, Set<String> visited,
                       Set<String> visiting, List<String> result) {
        if (!names.contains(name) || visited.contains(name)) {
            return;
        }
        if (!visiting.add(name)) {
            conflicts.add(new ProviderConflict(name, "Dependency cycle detected"));
            return;
        }
        ProviderManifest manifest = manifests.get(name);
        if (manifest != null) {
            for (String dependency : manifest.dependencies()) {
                visit(dependency, names, visited, visiting, result);
            }
        }
        visiting.remove(name);
        visited.add(name);
        result.add(name);
    }

    private static Set<String> names(java.util.Collection<ProviderManifest> manifests) {
        Set<String> names = new LinkedHashSet<>();
        manifests.forEach(manifest -> names.add(manifest.name()));
        return names;
    }
}

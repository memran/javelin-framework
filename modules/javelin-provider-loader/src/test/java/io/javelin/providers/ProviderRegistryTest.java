package io.javelin.providers;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.ServiceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProviderRegistryTest {
    @Test
    void versionsCompareAndCheckCompatibility() {
        assertTrue(ProviderVersion.parse("0.2.0").compareTo(ProviderVersion.parse("0.1.9")) > 0);
        assertTrue(ProviderVersion.compatible("0.1.0", "0.1.0", ""));
        assertFalse(ProviderVersion.compatible("0.1.0", "0.2.0", ""));
        assertFalse(ProviderVersion.compatible("1.0.0", "", "0.9.0"));
    }

    @Test
    void loadsEnabledProvidersInDependencyOrder(@TempDir Path dir) throws Exception {
        write(dir, "billing", manifest("billing", "1.0.0", List.of("core")));
        write(dir, "core", manifest("core", "1.0.0", List.of()));

        ProviderRegistry registry = registry(dir, List.of(), List.of(), false);

        assertEquals(2, registry.manifests().size());
        List<String> names = registry.providers().stream()
                .map(provider -> provider.getClass().getSimpleName())
                .toList();
        assertEquals(List.of("CoreProvider", "BillingProvider"), names);
        assertTrue(registry.conflicts().isEmpty());
    }

    @Test
    void reportsConflictsAndHonorsDisableList(@TempDir Path dir) throws Exception {
        write(dir, "broken", manifest("broken", "1.0.0", List.of("missing")));
        write(dir, "old", manifest("old", "1.0.0", List.of()).replace(
                "\"minJavelin\":\"\"", "\"minJavelin\":\"9.0.0\""));

        ProviderRegistry registry = registry(dir, List.of(), List.of("old"), false);

        assertTrue(registry.conflicts().stream().anyMatch(conflict ->
                conflict.message().contains("Missing dependency")));
        assertTrue(registry.conflicts().stream().anyMatch(conflict ->
                conflict.message().contains("Incompatible")));
        assertFalse(registry.enabled().stream().anyMatch(manifest -> manifest.name().equals("old")));
    }

    @Test
    void strictModeFailsOnConflicts(@TempDir Path dir) throws Exception {
        write(dir, "broken", manifest("broken", "1.0.0", List.of("missing")));
        ProviderRegistry registry = registry(dir, List.of(), List.of(), true);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, registry::providers);
    }

    @Test
    void registersDiscoveredProvidersIntoApplication(@TempDir Path dir) throws Exception {
        write(dir, "core", manifest("core", "1.0.0", List.of()));
        Application app = new Application(
                new TestConfig(Map.of("provider.directory", dir.toString())), key -> Optional.empty());
        app.register(new ProviderRegistryServiceProvider());
        assertTrue(app.has(ProviderRegistry.class));
    }

    private static ProviderRegistry registry(Path dir, List<String> enabled,
                                             List<String> disabled, boolean strict) {
        return new ProviderRegistry(dir, enabled, disabled, strict);
    }

    private static void write(Path dir, String name, String json) throws Exception {
        Path module = Files.createDirectories(dir.resolve(name));
        Files.writeString(module.resolve(ProviderManifest.FILE), json, StandardCharsets.UTF_8);
    }

    private static String manifest(String name, String version, List<String> dependencies) {
        String providerClass = name.equals("core") ? CoreProvider.class.getName() : BillingProvider.class.getName();
        return new ProviderManifest(name, providerClass, version, "", "", dependencies, true).toJson();
    }

    public static final class CoreProvider implements ServiceProvider {
        @Override public void register(Application app) { }
    }

    public static final class BillingProvider implements ServiceProvider {
        @Override public void register(Application app) { }
    }

    private static final class TestConfig implements Config {
        private final Map<String, String> values;
        private TestConfig(Map<String, String> values) { this.values = values; }
        @Override public Optional<String> getString(String key) { return Optional.ofNullable(values.get(key)); }
        @Override public Optional<Integer> getInt(String key) { return Optional.empty(); }
        @Override public Optional<Boolean> getBoolean(String key) { return Optional.empty(); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }
}

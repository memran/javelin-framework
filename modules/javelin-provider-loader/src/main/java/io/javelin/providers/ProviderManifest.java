package io.javelin.providers;

import io.javelin.core.Json;

import java.util.List;

/** Descriptor shipped by a provider JAR or local module directory. */
public record ProviderManifest(String name, String providerClass, String version,
                               String minJavelin, String maxJavelin,
                               List<String> dependencies, boolean enabled) {
    public static final String RESOURCE = "META-INF/javelin/provider.json";
    public static final String FILE = "provider.json";

    public ProviderManifest {
        name = name == null ? "" : name.trim();
        providerClass = providerClass == null ? "" : providerClass.trim();
        version = version == null || version.isBlank() ? "0.0.0" : version.trim();
        minJavelin = minJavelin == null ? "" : minJavelin.trim();
        maxJavelin = maxJavelin == null ? "" : maxJavelin.trim();
        dependencies = dependencies == null ? List.of() : List.copyOf(dependencies);
    }

    public static ProviderManifest fromJson(String json) {
        return Json.parse(json, ProviderManifest.class);
    }

    public String toJson() {
        return Json.stringify(this);
    }
}

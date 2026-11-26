package io.javelin.providers;

/** A distribution problem found while inspecting provider manifests. */
public record ProviderConflict(String provider, String message) {
    @Override
    public String toString() {
        return provider + ": " + message;
    }
}

package io.javelin.config;

import java.util.List;
import java.util.Optional;

/** Tries providers in order. Environment values take precedence over files. */
public final class CompositeSecretProvider implements SecretProvider {
    private final List<SecretProvider> providers;

    public CompositeSecretProvider(List<SecretProvider> providers) {
        this.providers = List.copyOf(providers);
    }

    @Override
    public Optional<String> get(String name) {
        for (SecretProvider provider : providers) {
            Optional<String> value = provider.get(name);
            if (value.isPresent()) {
                return value;
            }
        }
        return Optional.empty();
    }
}

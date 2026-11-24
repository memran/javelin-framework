package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ProviderLoaderTest {
    @Test
    void loadsConfiguredProvidersInDependencyOrder() {
        LoadedOrder.events.clear();
        TestConfig config = new TestConfig(Map.of("providers", List.of(
                DependentLoadedProvider.class.getName(),
                BaseLoadedProvider.class.getName()
        )));
        Application app = new Application(config, key -> Optional.empty());

        new ProviderLoader().load(app);

        assertEquals(List.of("base", "dependent"), LoadedOrder.events);
    }

    static final class LoadedOrder {
        static final List<String> events = new ArrayList<>();
    }

    public static final class BaseLoadedProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
            LoadedOrder.events.add("base");
        }
    }

    public static final class DependentLoadedProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
            LoadedOrder.events.add("dependent");
        }

        @Override
        public List<Class<? extends ServiceProvider>> dependsOn() {
            return List.of(BaseLoadedProvider.class);
        }
    }
}

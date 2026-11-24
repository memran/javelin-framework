package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ApplicationTest {
    @Test
    void shutdownClosesOwnedResourcesAndNotifiesProvidersInReverseOrder() {
        List<String> events = new ArrayList<>();
        Application app = application();
        app.instance(FirstResource.class, new FirstResource(events));
        app.instance(SecondResource.class, new SecondResource(events));
        app.register(new RecordingProvider(events));

        app.shutdown();
        app.shutdown();

        assertEquals(List.of("close-second", "close-first", "provider-shutdown"), events);
    }

    @Test
    void registerProvidersHonoursDeclaredDependencies() {
        List<String> order = new ArrayList<>();
        Application app = application();

        app.registerProviders(List.of(new DependentProvider(order), new BaseProvider(order)));

        assertEquals(List.of("base", "dependent"), order);
    }

    private static Application application() {
        return new Application(new TestConfig(Map.of()), key -> Optional.empty());
    }

    static final class FirstResource implements AutoCloseable {
        private final List<String> events;

        FirstResource(List<String> events) {
            this.events = events;
        }

        @Override
        public void close() {
            events.add("close-first");
        }
    }

    static final class SecondResource implements AutoCloseable {
        private final List<String> events;

        SecondResource(List<String> events) {
            this.events = events;
        }

        @Override
        public void close() {
            events.add("close-second");
        }
    }

    static final class RecordingProvider implements ServiceProvider {
        private final List<String> events;

        RecordingProvider(List<String> events) {
            this.events = events;
        }

        @Override
        public void register(Application app) {
        }

        @Override
        public void shutdown(Application app) {
            events.add("provider-shutdown");
        }
    }

    static final class BaseProvider implements ServiceProvider {
        private final List<String> order;

        BaseProvider(List<String> order) {
            this.order = order;
        }

        @Override
        public void register(Application app) {
            order.add("base");
        }
    }

    static final class DependentProvider implements ServiceProvider {
        private final List<String> order;

        DependentProvider(List<String> order) {
            this.order = order;
        }

        @Override
        public void register(Application app) {
            order.add("dependent");
        }

        @Override
        public List<Class<? extends ServiceProvider>> dependsOn() {
            return List.of(BaseProvider.class);
        }
    }
}

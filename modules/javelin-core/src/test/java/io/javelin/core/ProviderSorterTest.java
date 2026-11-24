package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProviderSorterTest {
    @Test
    void sortsIndependentProvidersByOrder() {
        List<ServiceProvider> sorted = new ProviderSorter().sort(List.of(
                new ThirdProvider(), new FirstProvider(), new SecondProvider()));

        assertEquals(List.of("first", "second", "third"), names(sorted));
    }

    @Test
    void dependenciesRunBeforeTheDependentProviderRegardlessOfOrder() {
        List<ServiceProvider> sorted = new ProviderSorter().sort(List.of(
                new NeedsFirstProvider(), new ThirdProvider(), new FirstProvider()));

        assertEquals(List.of("first", "needs-first", "third"), names(sorted));
    }

    @Test
    void rejectsDuplicateProviderTypes() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProviderSorter().sort(List.of(new FirstProvider(), new FirstProvider())));
    }

    @Test
    void rejectsCircularProviderDependencies() {
        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> new ProviderSorter().sort(List.of(new CycleA(), new CycleB())));

        assertTrue(error.getMessage().contains("Circular provider dependency"));
    }

    private static List<String> names(List<ServiceProvider> providers) {
        return providers.stream().map(provider -> {
            if (provider instanceof FirstProvider) return "first";
            if (provider instanceof SecondProvider) return "second";
            if (provider instanceof ThirdProvider) return "third";
            if (provider instanceof NeedsFirstProvider) return "needs-first";
            return provider.getClass().getSimpleName();
        }).toList();
    }

    static class FirstProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public int order() {
            return 0;
        }
    }

    static class SecondProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public int order() {
            return 10;
        }
    }

    static class ThirdProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public int order() {
            return 20;
        }
    }

    static class NeedsFirstProvider implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public List<Class<? extends ServiceProvider>> dependsOn() {
            return List.of(FirstProvider.class);
        }

        @Override
        public int order() {
            return -100;
        }
    }

    static class CycleA implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public List<Class<? extends ServiceProvider>> dependsOn() {
            return List.of(CycleB.class);
        }
    }

    static class CycleB implements ServiceProvider {
        @Override
        public void register(Application app) {
        }

        @Override
        public List<Class<? extends ServiceProvider>> dependsOn() {
            return List.of(CycleA.class);
        }
    }
}

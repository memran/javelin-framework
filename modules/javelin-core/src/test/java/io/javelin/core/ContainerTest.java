package io.javelin.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ContainerTest {
    @Test
    void resolvesConstructorDependencies() {
        Container container = new Container();
        container.singleton(Dependency.class, Dependency::new);

        Service service = container.make(Service.class);

        assertTrue(container.has(Dependency.class));
        assertSame(container.make(Dependency.class), service.dependency());
    }

    @Test
    void resolvesNestedSingletonDependencies() {
        Container container = new Container();
        container.singleton(Dependency.class, Dependency::new);
        container.singleton(Service.class, () -> new Service(container.make(Dependency.class)));

        Service service = container.make(Service.class);

        assertSame(container.make(Dependency.class), service.dependency());
        assertSame(service, container.make(Service.class));
    }

    @Test
    void factoryBindingsReturnAFreshInstanceEachTime() {
        Container container = new Container();
        container.factory(Dependency.class, Dependency::new);

        assertNotSame(container.make(Dependency.class), container.make(Dependency.class));
    }

    @Test
    void detectsCircularDependenciesAndRecoversTheResolutionStack() {
        Container container = new Container();

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> container.make(CircularA.class));

        assertTrue(error.getMessage().contains("Circular dependency detected"));
        assertTrue(error.getMessage().contains("CircularA -> CircularB -> CircularA"));
        assertNotNull(container.make(Dependency.class));
    }

    @Test
    void tracksSingletonCloseablesWithoutDuplicates() {
        Container container = new Container();
        container.singleton(CloseableResource.class, CloseableResource::new);

        CloseableResource first = container.make(CloseableResource.class);
        CloseableResource second = container.make(CloseableResource.class);

        assertSame(first, second);
        assertEquals(1, container.closeables().size());
    }

    static final class CircularA {
        CircularA(CircularB b) {
        }
    }

    static final class CircularB {
        CircularB(CircularA a) {
        }
    }

    static final class CloseableResource implements AutoCloseable {
        @Override
        public void close() {
        }
    }

    static final class Dependency {
    }

    record Service(Dependency dependency) {
    }
}

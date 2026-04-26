package io.javelin.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
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

    static final class Dependency {
    }

    record Service(Dependency dependency) {
    }
}

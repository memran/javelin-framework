package io.javelin.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Stable topological sort of providers by {@code dependsOn()} then {@code order()}. */
final class ProviderSorter {
    List<ServiceProvider> sort(List<ServiceProvider> providers) {
        List<ServiceProvider> byOrder = new ArrayList<>(providers);
        byOrder.sort(Comparator.comparingInt(ServiceProvider::order));

        Map<Class<?>, ServiceProvider> byType = new LinkedHashMap<>();
        for (ServiceProvider provider : byOrder) {
            if (byType.putIfAbsent(provider.getClass(), provider) != null) {
                throw new IllegalArgumentException("Duplicate provider type: " + provider.getClass().getName());
            }
        }

        Set<Class<?>> visited = new HashSet<>();
        Set<Class<?>> visiting = new HashSet<>();
        List<ServiceProvider> ordered = new ArrayList<>();
        for (ServiceProvider provider : byOrder) {
            visit(provider, byType, visited, visiting, ordered);
        }
        return ordered;
    }

    private void visit(ServiceProvider provider, Map<Class<?>, ServiceProvider> byType,
                       Set<Class<?>> visited, Set<Class<?>> visiting, List<ServiceProvider> ordered) {
        Class<?> type = provider.getClass();
        if (visited.contains(type)) {
            return;
        }
        if (!visiting.add(type)) {
            throw new IllegalStateException("Circular provider dependency: " + type.getName());
        }
        for (Class<? extends ServiceProvider> dependency : provider.dependsOn()) {
            ServiceProvider dependencyProvider = byType.get(dependency);
            if (dependencyProvider != null) {
                visit(dependencyProvider, byType, visited, visiting, ordered);
            }
        }
        visiting.remove(type);
        visited.add(type);
        ordered.add(provider);
    }
}

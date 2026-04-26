package io.javelin.view.pebble;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Extension;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.extension.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PebbleViewExtensions {
    private final List<Extension> extensions = new ArrayList<>();
    private final Map<String, Filter> filters = new LinkedHashMap<>();
    private final Map<String, Function> functions = new LinkedHashMap<>();
    private final Map<String, Test> tests = new LinkedHashMap<>();
    private final Map<String, Object> globals = new LinkedHashMap<>();

    public PebbleViewExtensions extension(Extension extension) {
        extensions.add(Objects.requireNonNull(extension, "extension"));
        return this;
    }

    public PebbleViewExtensions filter(String name, Filter filter) {
        filters.put(requireName(name), Objects.requireNonNull(filter, "filter"));
        return this;
    }

    public PebbleViewExtensions function(String name, Function function) {
        functions.put(requireName(name), Objects.requireNonNull(function, "function"));
        return this;
    }

    public PebbleViewExtensions test(String name, Test test) {
        tests.put(requireName(name), Objects.requireNonNull(test, "test"));
        return this;
    }

    public PebbleViewExtensions global(String name, Object value) {
        globals.put(requireName(name), value);
        return this;
    }

    List<Extension> extensions() {
        List<Extension> result = new ArrayList<>(extensions);
        if (!filters.isEmpty() || !functions.isEmpty() || !tests.isEmpty() || !globals.isEmpty()) {
            result.add(new RegisteredExtension(filters, functions, tests, globals));
        }
        return result;
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Pebble extension name must not be blank");
        }
        return name;
    }

    private static final class RegisteredExtension extends AbstractExtension {
        private final Map<String, Filter> filters;
        private final Map<String, Function> functions;
        private final Map<String, Test> tests;
        private final Map<String, Object> globals;

        private RegisteredExtension(
                Map<String, Filter> filters,
                Map<String, Function> functions,
                Map<String, Test> tests,
                Map<String, Object> globals
        ) {
            this.filters = Map.copyOf(filters);
            this.functions = Map.copyOf(functions);
            this.tests = Map.copyOf(tests);
            this.globals = Map.copyOf(globals);
        }

        @Override
        public Map<String, Filter> getFilters() {
            return filters;
        }

        @Override
        public Map<String, Function> getFunctions() {
            return functions;
        }

        @Override
        public Map<String, Test> getTests() {
            return tests;
        }

        @Override
        public Map<String, Object> getGlobalVariables() {
            return globals;
        }
    }
}

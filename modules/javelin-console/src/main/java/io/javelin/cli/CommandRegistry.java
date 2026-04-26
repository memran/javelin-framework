package io.javelin.cli;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class CommandRegistry {
    private final Map<String, Supplier<Object>> commands = new LinkedHashMap<>();

    public CommandRegistry register(String name, Supplier<Object> factory) {
        commands.put(name, factory);
        return this;
    }

    public Set<String> names() {
        return commands.keySet();
    }

    public Object create(String name) {
        Supplier<Object> factory = commands.get(name);
        if (factory == null) {
            throw new CliException("Unknown command: " + name, "Run `javelin --help` to see available commands.");
        }
        return factory.get();
    }
}

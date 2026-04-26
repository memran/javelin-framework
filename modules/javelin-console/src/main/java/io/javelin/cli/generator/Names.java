package io.javelin.cli.generator;

import java.util.Locale;

public final class Names {
    private Names() {
    }

    public static String className(String raw, String suffix) {
        String base = raw.replace('\\', '/');
        int slash = base.lastIndexOf('/');
        if (slash >= 0) {
            base = base.substring(slash + 1);
        }
        base = base.replaceAll("[^A-Za-z0-9]", " ");
        StringBuilder result = new StringBuilder();
        for (String part : base.split(" ")) {
            if (part.isBlank()) {
                continue;
            }
            result.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
            if (part.length() > 1) {
                result.append(part.substring(1));
            }
        }
        String name = result.isEmpty() ? "Generated" : result.toString();
        return name.endsWith(suffix) ? name : name + suffix;
    }

    public static String packageName(String raw, String rootPackage) {
        String normalized = raw.replace('\\', '/');
        int slash = normalized.lastIndexOf('/');
        if (slash < 0) {
            return rootPackage;
        }
        String folders = normalized.substring(0, slash).replace('/', '.').replaceAll("[^A-Za-z0-9_.]", "");
        return folders.isBlank() ? rootPackage : rootPackage + "." + folders.toLowerCase(Locale.ROOT);
    }

    public static String snake(String raw) {
        return raw.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("[^A-Za-z0-9]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "")
                .toLowerCase(Locale.ROOT);
    }
}

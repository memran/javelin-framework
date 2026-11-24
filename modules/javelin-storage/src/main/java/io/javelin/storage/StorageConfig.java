package io.javelin.storage;

import io.javelin.core.Config;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Named disk settings read from explicit {@code storage.*} keys. */
public final class StorageConfig {
    private static final String PREFIX = "storage.disks.";

    private final String defaultDisk;
    private final Map<String, DiskConfig> disks;

    private StorageConfig(String defaultDisk, Map<String, DiskConfig> disks) {
        this.defaultDisk = defaultDisk;
        this.disks = Map.copyOf(disks);
    }

    public record DiskConfig(String name, String driver, Path root, String basePath, String domain,
                             String endpoint, String accessKey, String secretKey, String bucket,
                             boolean publicDisk, long maxBytes, List<String> allowedExtensions,
                             List<String> allowedMime) {
        public boolean validate(byte[] content, String filename, String contentType) {
            if (content.length > maxBytes && maxBytes > 0) {
                return false;
            }
            String lower = filename.toLowerCase(java.util.Locale.ROOT);
            if (!allowedExtensions.isEmpty()
                    && allowedExtensions.stream().noneMatch(extension -> lower.endsWith("." + extension.toLowerCase(java.util.Locale.ROOT)))) {
                return false;
            }
            return allowedMime.isEmpty() || allowedMime.contains(contentType);
        }
    }

    public static StorageConfig from(Config config) {
        Set<String> names = new LinkedHashSet<>();
        for (String key : config.keys()) {
            if (key.startsWith(PREFIX)) {
                String rest = key.substring(PREFIX.length());
                int dot = rest.indexOf('.');
                names.add(dot < 0 ? rest : rest.substring(0, dot));
            }
        }
        if (names.isEmpty()) {
            names.add(config.getString("storage.default", "public"));
        }
        Map<String, DiskConfig> disks = new LinkedHashMap<>();
        for (String name : names) {
            disks.put(name, disk(config, name));
        }
        String configuredDefault = config.getString("storage.default", "public");
        return new StorageConfig(disks.containsKey(configuredDefault) ? configuredDefault : names.iterator().next(), disks);
    }

    private static DiskConfig disk(Config config, String name) {
        String prefix = PREFIX + name + ".";
        String driver = config.getString(prefix + "driver", "local");
        String defaultRoot = name.equals("temporary")
                ? System.getProperty("java.io.tmpdir")
                : "storage/" + name;
        return new DiskConfig(
                name,
                driver,
                Path.of(config.getString(prefix + "root", defaultRoot)),
                config.getString(prefix + "base_path", ""),
                config.getString(prefix + "domain", "/files/"),
                config.getString(prefix + "endpoint", ""),
                config.getString(prefix + "access_key", ""),
                config.getString(prefix + "secret_key", ""),
                config.getString(prefix + "bucket", ""),
                config.getBoolean(prefix + "public").orElse(!name.equals("private")),
                config.getLong(prefix + "max_bytes", 10 * 1024 * 1024L),
                config.getStringList(prefix + "allowed_extensions"),
                config.getStringList(prefix + "allowed_mime"));
    }

    public String defaultDisk() {
        return defaultDisk;
    }

    public Map<String, DiskConfig> disks() {
        return disks;
    }
}

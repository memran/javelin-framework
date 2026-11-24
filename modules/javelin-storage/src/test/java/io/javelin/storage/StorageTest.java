package io.javelin.storage;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StorageTest {
    @Test
    void localDiskSupportsCrudListingAndUrls(@TempDir Path root) {
        StorageManager manager = manager(root);
        io.javelin.storages.Storage.use(manager);
        Disk disk = manager.disk("public");

        StoredFile stored = disk.put("photos/hello.txt", "hello storage".getBytes(StandardCharsets.UTF_8),
                "hello.txt", "text/plain");

        assertTrue(stored.url().endsWith("photos/hello.txt"), stored.url());
        assertEquals("hello storage", new String(disk.get("photos/hello.txt").orElseThrow(), StandardCharsets.UTF_8));
        assertTrue(disk.exists("photos/hello.txt"));
        assertEquals(13, disk.size("photos/hello.txt"));
        assertTrue(disk.list("photos").stream().anyMatch(path -> path.endsWith("hello.txt")));

        assertTrue(disk.copy("photos/hello.txt", "copies/hello.txt"));
        assertTrue(disk.exists("copies/hello.txt"));
        assertTrue(disk.move("copies/hello.txt", "moved/hello.txt"));
        assertFalse(disk.exists("copies/hello.txt"));
        assertTrue(disk.delete("moved/hello.txt"));
    }

    @Test
    void facadePutAndPrivateDownloadPolicy(@TempDir Path root) {
        StorageManager manager = manager(root);
        io.javelin.storages.Storage.use(manager);

        StoredFile stored = io.javelin.storages.Storage.put("public", "notes/facade.txt",
                "facade".getBytes(StandardCharsets.UTF_8));
        assertTrue(stored.url().contains("notes/facade.txt"));

        io.javelin.storages.Storage.put("private", "secret.txt",
                "secret".getBytes(StandardCharsets.UTF_8));
        Request anonymous = request(null);
        Response forbidden = manager.download("private", "secret.txt", anonymous);
        assertEquals(403, forbidden.status());

        Request authenticated = request(new Authenticated("alice", Set.of()));
        Response allowed = manager.download("private", "secret.txt", authenticated);
        assertEquals(200, allowed.status());
        assertEquals("secret", new String(allowed.body(), StandardCharsets.UTF_8));
    }

    @Test
    void rejectsPathTraversal(@TempDir Path root) {
        Disk disk = manager(root).disk("public");
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> disk.put("../escape.txt", "bad".getBytes(StandardCharsets.UTF_8)));
    }

    private static StorageManager manager(Path root) {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("storage.default", "public");
        for (String name : List.of("public", "private", "temporary")) {
            values.put("storage.disks." + name + ".driver", "local");
            values.put("storage.disks." + name + ".root", root.resolve(name).toString());
            values.put("storage.disks." + name + ".domain", "/files/");
            values.put("storage.disks." + name + ".max_bytes", "1048576");
        }
        Application app = new Application(new TestConfig(values), key -> Optional.empty());
        app.register(new StorageServiceProvider());
        app.boot();
        return app.make(StorageManager.class);
    }

    private static Request request(Authenticated principal) {
        Request request = new Request(HttpMethod.GET, "/files/secret.txt", Map.of(), Map.of(), Map.of(),
                new byte[0], "127.0.0.1");
        return principal == null ? request : request.withAttribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE, principal);
    }

    private record TestConfig(Map<String, String> values) implements Config {
        @Override public Set<String> keys() { return Set.copyOf(values.keySet()); }
        @Override public Optional<String> getString(String key) { return Optional.ofNullable(values.get(key)); }
        @Override public Optional<Integer> getInt(String key) { return getString(key).map(Integer::parseInt); }
        @Override public Optional<Boolean> getBoolean(String key) { return getString(key).map(Boolean::parseBoolean); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }
}

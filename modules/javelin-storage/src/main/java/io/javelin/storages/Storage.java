package io.javelin.storages;

import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.storage.Disk;
import io.javelin.storage.StoredFile;
import io.javelin.storage.StorageManager;

import java.util.Objects;

/** Static facade for the active {@link StorageManager}. */
public final class Storage {
    private static volatile StorageManager active;

    private Storage() {
    }

    public static void use(StorageManager manager) {
        active = Objects.requireNonNull(manager, "manager");
    }

    public static StorageManager active() {
        return active;
    }

    public static Disk disk(String name) {
        return active().disk(name);
    }

    public static StoredFile put(String disk, String path, byte[] content) {
        return disk(disk).put(path, content);
    }

    public static Response download(String disk, String path, Request request) {
        return active().download(disk, path, request);
    }
}

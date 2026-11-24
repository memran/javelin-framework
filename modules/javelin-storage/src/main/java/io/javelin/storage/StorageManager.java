package io.javelin.storage;

import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Explicit registry of named disks with one default. */
public final class StorageManager {
    private final Map<String, Disk> disks;
    private final String defaultDisk;
    private final DownloadPolicy policy;

    public StorageManager(Map<String, Disk> disks, String defaultDisk, DownloadPolicy policy) {
        if (!disks.containsKey(defaultDisk)) {
            throw new IllegalArgumentException("Unknown default disk: " + defaultDisk);
        }
        this.disks = Map.copyOf(new LinkedHashMap<>(disks));
        this.defaultDisk = defaultDisk;
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public Disk disk(String name) {
        Disk disk = disks.get(name);
        if (disk == null) {
            throw new IllegalArgumentException("Unknown storage disk: " + name);
        }
        return disk;
    }

    public Disk defaultDisk() {
        return disk(defaultDisk);
    }

    /** The underlying x-file-storage service, for advanced platform operations. */
    public org.dromara.x.file.storage.core.FileStorageService service() {
        return disks.values().stream()
                .filter(XFileDisk.class::isInstance)
                .map(XFileDisk.class::cast)
                .findFirst()
                .map(XFileDisk::service)
                .orElseThrow();
    }

    public Set<String> names() {
        return disks.keySet();
    }

    /** Downloads from a private disk only when the policy allows the request. */
    public Response download(String diskName, String path, Request request) {
        Disk disk = disk(diskName);
        if (!disk.isPublic() && !policy.allow(diskName, path, request)) {
            return Response.text("Forbidden", 403);
        }
        return disk.download(path).orElseGet(() -> Response.text("Not Found", 404));
    }
}

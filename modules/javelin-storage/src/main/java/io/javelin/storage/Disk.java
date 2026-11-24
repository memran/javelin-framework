package io.javelin.storage;

import io.javelin.core.Response;

import java.util.List;
import java.util.Optional;

/** Explicit storage adapter contract. Implementations delegate to a backend. */
public interface Disk {
    String name();

    /** Public disks can be downloaded without authorization. */
    boolean isPublic();

    StoredFile put(String path, byte[] content, String filename, String contentType);

    /** Convenience overload: derives the filename from the path. */
    default StoredFile put(String path, byte[] content) {
        String name = path.contains("/") ? path.substring(path.lastIndexOf('/') + 1) : path;
        return put(path, content, name, null);
    }

    Optional<byte[]> get(String path);

    boolean exists(String path);

    boolean delete(String path);

    boolean copy(String source, String target);

    boolean move(String source, String target);

    List<String> list(String directory);

    long size(String path);

    String url(String path);

    /** Streams a file as a download response; callers decide authorization. */
    Optional<Response> download(String path);
}

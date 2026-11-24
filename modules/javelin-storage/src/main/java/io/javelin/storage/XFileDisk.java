package io.javelin.storage;

import io.javelin.core.Response;
import io.javelin.support.Security;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;

import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/** Disk backed by the x-file-storage FileStorageService. */
public final class XFileDisk implements Disk {
    private final FileStorageService service;
    private final StorageConfig.DiskConfig config;

    public XFileDisk(FileStorageService service, StorageConfig.DiskConfig config) {
        this.service = service;
        this.config = config;
    }

    @Override
    public String name() {
        return config.name();
    }

    @Override
    public StoredFile put(String path, byte[] content, String filename, String contentType) {
        String safePath = safePath(path);
        String safeName = Security.sanitizeFilename(filename);
        if (!config.validate(content, safeName, contentType == null ? "" : contentType)) {
            throw new IllegalArgumentException("File rejected by disk policy: " + safeName);
        }
        String parent = parent(safePath);
        String type = contentType == null || contentType.isBlank()
                ? URLConnection.guessContentTypeFromName(safeName)
                : contentType;
        FileInfo info = service.of(content)
                .setOriginalFilename(safeName)
                .setSaveFilename(safeName)
                .setPath(parent)
                .setPlatform(config.name())
                .setContentType(type == null ? "application/octet-stream" : type)
                .upload();
        return new StoredFile(info.getUrl(), safePath, info.getFilename(),
                info.getSize() == null ? content.length : info.getSize(), info.getContentType());
    }

    @Override
    public Optional<byte[]> get(String path) {
        try {
            return Optional.ofNullable(service.download(url(path)).bytes());
        } catch (RuntimeException exception) {
            return Optional.empty();
        }
    }

    @Override
    public boolean exists(String path) {
        return service.exists(url(path));
    }

    @Override
    public boolean delete(String path) {
        return service.delete(url(path));
    }

    @Override
    public boolean copy(String source, String target) {
        FileInfo info = service.getFileInfoByUrl(url(source));
        if (info == null) {
            return false;
        }
        service.copy(info).setPath(parent(target)).setFilename(name(target)).copy();
        return true;
    }

    @Override
    public boolean move(String source, String target) {
        FileInfo info = service.getFileInfoByUrl(url(source));
        if (info == null) {
            return false;
        }
        service.move(info).setPath(parent(target)).setFilename(name(target)).move();
        return true;
    }

    @Override
    public List<String> list(String directory) {
        String prefix = safePath(directory);
        return service.listFiles()
                .setPlatform(config.name())
                .setPath(prefix.isEmpty() ? "" : prefix.endsWith("/") ? prefix : prefix + "/")
                .listFiles()
                .getFileList()
                .stream()
                .map(file -> join(prefix, file.getPath(), file.getFilename()))
                .toList();
    }

    @Override
    public long size(String path) {
        FileInfo info = service.getFileInfoByUrl(url(path));
        return info == null || info.getSize() == null ? 0 : info.getSize();
    }

    @Override
    public String url(String path) {
        String domain = config.domain();
        String base = config.basePath().isEmpty() ? config.name() + "/" : config.basePath();
        String normalized = domain.endsWith("/") ? domain : domain + "/";
        if (!base.isBlank()) {
            normalized += base.endsWith("/") ? base : base + "/";
        }
        return normalized + safePath(path);
    }

    @Override
    public Optional<Response> download(String path) {
        return get(path).map(bytes -> Response.download(bytes, name(path),
                URLConnection.guessContentTypeFromName(name(path)) == null
                        ? "application/octet-stream"
                        : URLConnection.guessContentTypeFromName(name(path))));
    }

    public boolean isPublic() {
        return config.publicDisk();
    }

    public FileStorageService service() {
        return service;
    }

    private static String join(String prefix, String path, String filename) {
        String base = prefix == null ? "" : prefix;
        String middle = path == null ? "" : path;
        String result = base;
        if (!middle.isBlank()) {
            result += middle.endsWith("/") ? middle : middle + "/";
        }
        return result + (filename == null ? "" : filename);
    }

    private static String safePath(String path) {
        if (path == null) {
            return "";
        }
        String normalized = path.replace('\\', '/');
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        for (String segment : normalized.split("/")) {
            if (segment.equals("..")) {
                throw new IllegalArgumentException("Path traversal is not allowed: " + path);
            }
        }
        return normalized;
    }

    private static String parent(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? "" : path.substring(0, slash + 1);
    }

    private static String name(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? path : path.substring(slash + 1);
    }
}

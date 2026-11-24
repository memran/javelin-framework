package io.javelin.storage;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory record of uploaded files, used by x-file-storage to resolve URLs.
 * Register a persistent FileRecorder provider for multi-instance deployments.
 */
public final class InMemoryFileRecorder implements FileRecorder {
    private final Map<String, FileInfo> files = new ConcurrentHashMap<>();

    @Override
    public boolean save(FileInfo fileInfo) {
        files.put(fileInfo.getUrl(), fileInfo);
        return true;
    }

    @Override
    public void update(FileInfo fileInfo) {
        files.put(fileInfo.getUrl(), fileInfo);
    }

    @Override
    public FileInfo getByUrl(String url) {
        return files.get(url);
    }

    @Override
    public boolean delete(String url) {
        return files.remove(url) != null;
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        // Multipart uploads are not tracked in memory.
    }

    @Override
    public void deleteFilePartByUploadId(String uploadId) {
        // Multipart uploads are not tracked in memory.
    }
}

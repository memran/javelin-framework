package io.javelin.storage;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.FileStorageServiceBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Wires named disks onto x-file-storage platforms. */
public final class StorageServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(StorageConfig.class, () -> StorageConfig.from(app.config()));
        app.singleton(FileStorageService.class, () -> service(app.make(StorageConfig.class)));
        app.singleton(DownloadPolicy.class, AuthenticatedDownloadPolicy::new);
        app.singleton(StorageManager.class, () -> manager(
                app.make(StorageConfig.class),
                app.make(FileStorageService.class),
                app.make(DownloadPolicy.class)));
    }

    @Override
    public void boot(Application app) {
        io.javelin.storages.Storage.use(app.make(StorageManager.class));
    }

    private StorageManager manager(StorageConfig config, FileStorageService service, DownloadPolicy policy) {
        Map<String, Disk> disks = new LinkedHashMap<>();
        for (StorageConfig.DiskConfig disk : config.disks().values()) {
            disks.put(disk.name(), new XFileDisk(service, disk));
        }
        return new StorageManager(disks, config.defaultDisk(), policy);
    }

    private FileStorageService service(StorageConfig config) {
        FileStorageProperties properties = new FileStorageProperties();
        properties.setDefaultPlatform(config.defaultDisk());

        List<FileStorageProperties.LocalPlusConfig> local = new ArrayList<>();
        List<FileStorageProperties.MinioConfig> minio = new ArrayList<>();
        List<FileStorageProperties.AmazonS3Config> s3 = new ArrayList<>();

        for (StorageConfig.DiskConfig disk : config.disks().values()) {
            switch (disk.driver().toLowerCase(Locale.ROOT)) {
                case "local" -> local.add(local(disk));
                case "minio" -> minio.add(minio(disk));
                case "s3" -> s3.add(s3(disk));
                default -> throw new IllegalArgumentException(
                        "Unknown storage driver '" + disk.driver() + "' for disk " + disk.name());
            }
        }

        properties.setLocalPlus(local);
        properties.setMinio(minio);
        properties.setAmazonS3(s3);
        return FileStorageServiceBuilder.create(properties)
                .useDefault()
                .setFileRecorder(new InMemoryFileRecorder())
                .addByteFileWrapperAdapter()
                .build();
    }

    private static FileStorageProperties.LocalPlusConfig local(StorageConfig.DiskConfig disk) {
        FileStorageProperties.LocalPlusConfig config = new FileStorageProperties.LocalPlusConfig();
        config.setPlatform(disk.name());
        config.setBasePath(disk.basePath().isEmpty() ? disk.name() + "/" : disk.basePath());
        config.setStoragePath(disk.root().toAbsolutePath().toString());
        config.setDomain(disk.domain());
        return config;
    }

    private static FileStorageProperties.MinioConfig minio(StorageConfig.DiskConfig disk) {
        FileStorageProperties.MinioConfig config = new FileStorageProperties.MinioConfig();
        config.setPlatform(disk.name());
        config.setAccessKey(disk.accessKey());
        config.setSecretKey(disk.secretKey());
        config.setEndPoint(disk.endpoint());
        config.setBucketName(disk.bucket());
        config.setDomain(disk.domain());
        config.setBasePath(disk.basePath());
        return config;
    }

    private static FileStorageProperties.AmazonS3Config s3(StorageConfig.DiskConfig disk) {
        FileStorageProperties.AmazonS3Config config = new FileStorageProperties.AmazonS3Config();
        config.setPlatform(disk.name());
        config.setAccessKey(disk.accessKey());
        config.setSecretKey(disk.secretKey());
        config.setEndPoint(disk.endpoint());
        config.setBucketName(disk.bucket());
        config.setDomain(disk.domain());
        config.setBasePath(disk.basePath());
        return config;
    }
}

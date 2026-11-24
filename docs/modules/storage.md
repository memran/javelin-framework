# Storage Module

Artifact: `io.javelin:javelin-storage`

`javelin-storage` provides a unified `Disk` abstraction backed by [dromara/x-file-storage](https://github.com/dromara/x-file-storage). Generated projects register three local disks by default: `public`, `private` and `temporary`.

## Developer API

```java
import io.javelin.storages.Storage;
import io.javelin.storage.StoredFile;

StoredFile file = Storage.disk("public").put("photo.jpg", bytes);
String url = file.url();
```

`Storage.disk(name)` resolves a named disk from the active `StorageManager`. The facade is set by `StorageServiceProvider.boot()`; you can also inject `StorageManager` or `Disk` from the container. `Disk` exposes `put`, `get`, `exists`, `delete`, `copy`, `move`, `list`, `size`, `url` and `download`.

## Configuration

```yaml
storage:
  default: public
  disks:
    public:
      driver: local
      root: storage/app/public
      domain: /files/
      max_bytes: 10485760
      allowed_extensions: [txt, png, jpg, pdf]
      allowed_mime: []
    private:
      driver: local
      root: storage/app/private
      domain: /files/
      public: false
    temporary:
      driver: local
      root: storage/app/tmp
      domain: /files/
    assets:
      driver: minio          # or s3
      endpoint: http://localhost:9000
      access_key: ${MINIO_ACCESS_KEY:}
      secret_key: ${MINIO_SECRET_KEY:}
      bucket: uploads
      domain: http://localhost:9000/uploads/
      base_path: files/
```

| Key | Default | Meaning |
|---|---|---|
| `storage.default` | `public` | Disk used when no name is given |
| `storage.disks.<name>.driver` | `local` | `local`, `minio` or `s3` |
| `storage.disks.<name>.root` | `storage/<name>` | Filesystem root for local disks |
| `storage.disks.<name>.domain` | `/files/` | URL prefix for generated links |
| `storage.disks.<name>.base_path` | `<name>/` | Backend base path |
| `storage.disks.<name>.public` | `true` for all but `private` | Whether downloads skip authorization |
| `storage.disks.<name>.max_bytes` | `10485760` | Upload size limit |
| `storage.disks.<name>.allowed_extensions` | any | Extension allowlist |
| `storage.disks.<name>.allowed_mime` | any | MIME allowlist |

MinIO and S3 disks use the same `Disk` API; the AWS S3 and MinIO SDKs are bundled with this module.

## Safety

- Every path is normalized; `..` segments are rejected, and backslashes are treated as separators.
- Filenames pass through `io.javelin.support.Security.sanitizeFilename`.
- Uploads are checked against `max_bytes`, `allowed_extensions` and `allowed_mime` before being written.
- Private downloads go through `StorageManager.download(disk, path, request)`. A `DownloadPolicy` decides access; the default `AuthenticatedDownloadPolicy` requires an `Authenticated` principal.

```java
Response response = Storage.download("private", "contracts/2026.pdf", request);
// 403 unless the request has an authenticated principal
```

## File recorder

x-file-storage resolves URLs through a `FileRecorder`. This module provides `InMemoryFileRecorder`, which is sufficient for a single instance. Register a persistent `FileRecorder` provider for multi-instance deployments; otherwise uploaded files written by one instance cannot be resolved by another after restart.

## Service provider

`StorageServiceProvider` builds the x-file-storage `FileStorageService`, registers the manager and policy, and activates the static facade. Register it explicitly in `config.providers` (generated projects do this by default).

# Files and caching

This tutorial uses the runnable `demo-app` to show named disks, private downloads and fast local caching.

## 1. Disks and cache are configured explicitly

Generated apps include three local disks plus the Caffeine cache provider:

```yaml
cache:
  driver: memory          # or "valkey" to switch backends
  caffeine:
    maximum_size: 10000

storage:
  default: public
  disks:
    public:
      driver: local
      root: storage/app/public
      domain: /files/
      max_bytes: 10485760
    private:
      driver: local
      root: storage/app/private
      domain: /files/
      public: false
    temporary:
      driver: local
      root: storage/app/tmp
      domain: /files/
```

Add these providers to `config.providers`:

```yaml
  - io.javelin.storage.StorageServiceProvider
  - io.javelin.cache.caffeine.CaffeineCacheServiceProvider
```

## 2. Write and read files

```java
import io.javelin.storages.Storage;
import io.javelin.storage.StoredFile;

StoredFile file = Storage.disk("public").put("photo.jpg", bytes);
String url = file.url();
Optional<byte[]> content = Storage.disk("public").get("photo.jpg");
```

The demo exposes `POST /demo/files` for multipart uploads and returns the stored URL, filename and size. All paths are normalized, `..` segments are rejected, filenames are sanitized and uploads are checked against size/extension/MIME rules.

## 3. Private downloads

Public disks can be fetched directly. Private disks go through an authorization policy:

```java
Response response = Storage.download("private", "contract.pdf", request);
```

The default `AuthenticatedDownloadPolicy` returns 403 unless the request has an `Authenticated` principal (see the [security tutorial](secure-web-app.md)). Try it:

```bash
curl -i 'http://127.0.0.1:8080/demo/files/download?path=contract.pdf'
# 403 Forbidden
```

## 4. S3 or MinIO

Add a disk with `driver: minio` or `driver: s3` and backend credentials:

```yaml
    assets:
      driver: minio
      endpoint: http://localhost:9000
      access_key: ${MINIO_ACCESS_KEY:}
      secret_key: ${MINIO_SECRET_KEY:}
      bucket: uploads
      domain: http://localhost:9000/uploads/
```

`Storage.disk("assets")` then uses exactly the same API. The AWS S3 and MinIO SDKs are bundled with `javelin-storage`.

## 5. Caching

```java
import io.javelin.caches.Cache;
import java.time.Duration;

Cache.put("users_count", 100, Duration.ofMinutes(5));
Object value = Cache.get("users_count").orElse(null);
Cache.forget("users_count");
```

`GET /demo/cache` demonstrates the facade. Caffeine bounds the cache by `maximum_size` and evicts entries using its window/TinyLFU policy; each `put` supplies its own TTL. Setting `cache.driver: valkey` switches the same facade to the Valkey adapter without changing application code.

## Notes

- The bundled file recorder is in-memory. For multi-instance deployments, register a persistent x-file-storage `FileRecorder` so URL lookups survive restarts.
- Keep private disk roots outside `public/` and let the download policy control access.

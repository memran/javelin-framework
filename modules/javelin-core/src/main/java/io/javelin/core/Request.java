package io.javelin.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javelin.support.Input;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;

public final class Request {
    private static final ObjectMapper JSON = new ObjectMapper();

    private final HttpMethod method;
    private final String path;
    private final Map<String, List<String>> headers;
    private final Map<String, String> query;
    private final Map<String, String> params;
    private final byte[] body;
    private final String remoteAddress;
    private final Map<String, String> multipartFields;
    private final Map<String, List<UploadedFile>> uploads;

    public Request(HttpMethod method, String path, Map<String, List<String>> headers, Map<String, String> query,
                   Map<String, String> params, byte[] body, String remoteAddress) {
        this.method = method;
        this.path = path;
        this.headers = new LinkedHashMap<>(headers);
        this.query = new LinkedHashMap<>(query);
        this.params = new LinkedHashMap<>(params);
        this.body = body == null ? new byte[0] : body.clone();
        this.remoteAddress = remoteAddress;
        MultipartData multipart = parseMultipart(this.headers, this.body);
        this.multipartFields = multipart.fields();
        this.uploads = multipart.uploads();
    }

    public static Map<String, String> parseQuery(String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return Collections.emptyMap();
        }
        Map<String, String> values = new LinkedHashMap<>();
        for (String pair : rawQuery.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length == 2 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            values.put(key, value);
        }
        return values;
    }

    public HttpMethod method() {
        return method;
    }

    public String path() {
        return path;
    }

    public Optional<String> header(String name) {
        return headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst();
    }

    public Optional<String> query(String name) {
        return Optional.ofNullable(query.get(name));
    }

    public Optional<String> param(String name) {
        return Optional.ofNullable(params.get(name));
    }

    public byte[] body() {
        return body.clone();
    }

    public String bodyAsString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    public Input input() {
        Map<String, String> values = new LinkedHashMap<>(query);
        if (isFormUrlEncoded()) {
            values.putAll(parseQuery(bodyAsString()));
        }
        if (!multipartFields.isEmpty()) {
            values.putAll(multipartFields);
        }
        return Input.from(values);
    }

    public Optional<UploadedFile> file(String name) {
        Objects.requireNonNull(name, "name");
        return files(name).stream().findFirst();
    }

    public Optional<UploadedFile> upload(String name) {
        Objects.requireNonNull(name, "name");
        return file(name);
    }

    public List<UploadedFile> files(String name) {
        Objects.requireNonNull(name, "name");
        return uploads.getOrDefault(name, List.of());
    }

    public List<UploadedFile> uploads() {
        return uploads.values().stream()
                .flatMap(List::stream)
                .toList();
    }

    public Map<String, List<UploadedFile>> files() {
        Map<String, List<UploadedFile>> snapshot = new LinkedHashMap<>();
        for (Map.Entry<String, List<UploadedFile>> entry : uploads.entrySet()) {
            snapshot.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        return Map.copyOf(snapshot);
    }

    public Optional<Path> saveFile(String name, Path directory) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(directory, "directory");
        return file(name).map(upload -> upload.saveToDirectory(directory));
    }

    public List<Path> saveFiles(String name, Path directory) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(directory, "directory");
        return files(name).stream()
                .map(upload -> upload.saveToDirectory(directory))
                .toList();
    }

    public <T> T json(Class<T> type) {
        try {
            return JSON.readValue(body, type);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Invalid JSON request body", exception);
        }
    }

    public String remoteAddress() {
        return remoteAddress;
    }

    public Map<String, String> queryParameters() {
        return Map.copyOf(query);
    }

    Request withParams(Map<String, String> routeParams) {
        return new Request(method, path, headers, query, routeParams, body, remoteAddress);
    }

    private boolean isFormUrlEncoded() {
        return header("Content-Type")
                .map(value -> value.toLowerCase().contains("application/x-www-form-urlencoded"))
                .orElse(false);
    }

    private static MultipartData parseMultipart(Map<String, List<String>> headers, byte[] body) {
        String contentType = headerValue(headers, "Content-Type").orElse("");
        if (!contentType.toLowerCase().contains("multipart/form-data")) {
            return MultipartData.empty();
        }
        String boundary = boundaryOf(contentType);
        if (boundary.isBlank() || body.length == 0) {
            return MultipartData.empty();
        }
        return MultipartData.parse(boundary, body);
    }

    private static Optional<String> headerValue(Map<String, List<String>> headers, String name) {
        return headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst();
    }

    private static String boundaryOf(String contentType) {
        for (String part : contentType.split(";")) {
            String token = part.trim();
            if (token.toLowerCase().startsWith("boundary=")) {
                String boundary = token.substring("boundary=".length()).trim();
                if (boundary.startsWith("\"") && boundary.endsWith("\"") && boundary.length() >= 2) {
                    boundary = boundary.substring(1, boundary.length() - 1);
                }
                return boundary;
            }
        }
        return "";
    }

    private record MultipartData(Map<String, String> fields, Map<String, List<UploadedFile>> uploads) {
        private static MultipartData empty() {
            return new MultipartData(Map.of(), Map.of());
        }

        private static MultipartData parse(String boundary, byte[] body) {
            byte[] boundaryBytes = ("--" + boundary).getBytes(StandardCharsets.US_ASCII);
            byte[] delimiter = "\r\n\r\n".getBytes(StandardCharsets.US_ASCII);
            Map<String, String> fields = new LinkedHashMap<>();
            Map<String, List<UploadedFile>> uploads = new LinkedHashMap<>();

            int cursor = indexOf(body, boundaryBytes, 0);
            if (cursor < 0) {
                return empty();
            }
            cursor += boundaryBytes.length;
            cursor = skipLineBreak(body, cursor);

            while (cursor < body.length) {
                int nextBoundary = indexOf(body, boundaryBytes, cursor);
                if (nextBoundary < 0) {
                    break;
                }
                int partEnd = trimTrailingLineBreak(body, cursor, nextBoundary);
                if (partEnd <= cursor) {
                    break;
                }
                int headerEnd = indexOf(body, delimiter, cursor);
                if (headerEnd < 0 || headerEnd > partEnd) {
                    cursor = advanceBoundary(body, nextBoundary, boundaryBytes.length);
                    if (cursor < 0) {
                        break;
                    }
                    continue;
                }

                String headerBlock = new String(body, cursor, headerEnd - cursor, StandardCharsets.UTF_8);
                byte[] content = slice(body, headerEnd + delimiter.length, partEnd);
                Map<String, String> partHeaders = parseHeaders(headerBlock);
                PartDisposition disposition = parseDisposition(partHeaders.get("content-disposition"));
                if (disposition.name() != null && !disposition.name().isBlank()) {
                    if (disposition.filename() == null || disposition.filename().isBlank()) {
                        fields.put(disposition.name(), new String(content, StandardCharsets.UTF_8));
                    } else {
                        uploads.computeIfAbsent(disposition.name(), ignored -> new ArrayList<>())
                                .add(new UploadedFile(disposition.name(), disposition.filename(), partHeaders.get("content-type"), content));
                    }
                }

                cursor = advanceBoundary(body, nextBoundary, boundaryBytes.length);
                if (cursor < 0) {
                    break;
                }
            }

            return new MultipartData(Map.copyOf(fields), freezeUploads(uploads));
        }

        private static Map<String, List<UploadedFile>> freezeUploads(Map<String, List<UploadedFile>> uploads) {
            Map<String, List<UploadedFile>> snapshot = new LinkedHashMap<>();
            for (Map.Entry<String, List<UploadedFile>> entry : uploads.entrySet()) {
                snapshot.put(entry.getKey(), List.copyOf(entry.getValue()));
            }
            return Map.copyOf(snapshot);
        }

        private static Map<String, String> parseHeaders(String headerBlock) {
            Map<String, String> headers = new HashMap<>();
            for (String line : headerBlock.split("\r\n")) {
                int colon = line.indexOf(':');
                if (colon > 0) {
                    String key = line.substring(0, colon).trim().toLowerCase();
                    String value = line.substring(colon + 1).trim();
                    headers.put(key, value);
                }
            }
            return headers;
        }

        private static PartDisposition parseDisposition(String value) {
            if (value == null || value.isBlank()) {
                return new PartDisposition(null, null);
            }
            String name = null;
            String filename = null;
            for (String token : value.split(";")) {
                String part = token.trim();
                if (part.startsWith("name=")) {
                    name = unquote(part.substring(5));
                } else if (part.startsWith("filename=")) {
                    filename = unquote(part.substring(9));
                }
            }
            return new PartDisposition(name, filename);
        }

        private static String unquote(String value) {
            String result = value.trim();
            if (result.startsWith("\"") && result.endsWith("\"") && result.length() >= 2) {
                return result.substring(1, result.length() - 1);
            }
            return result;
        }

        private static int advanceBoundary(byte[] body, int boundaryIndex, int boundaryLength) {
            int index = boundaryIndex + boundaryLength;
            if (matches(body, index, "--")) {
                return body.length;
            }
            index = skipLineBreak(body, index);
            return index;
        }

        private static int skipLineBreak(byte[] body, int index) {
            if (index + 1 < body.length && body[index] == '\r' && body[index + 1] == '\n') {
                return index + 2;
            }
            return index;
        }

        private static int trimTrailingLineBreak(byte[] body, int start, int end) {
            int value = end;
            if (value - 2 >= start && body[value - 2] == '\r' && body[value - 1] == '\n') {
                return value - 2;
            }
            return value;
        }

        private static boolean matches(byte[] body, int index, String token) {
            if (index < 0 || index + token.length() > body.length) {
                return false;
            }
            for (int offset = 0; offset < token.length(); offset++) {
                if (body[index + offset] != (byte) token.charAt(offset)) {
                    return false;
                }
            }
            return true;
        }

        private static int indexOf(byte[] body, byte[] token, int fromIndex) {
            outer:
            for (int index = Math.max(0, fromIndex); index <= body.length - token.length; index++) {
                for (int offset = 0; offset < token.length; offset++) {
                    if (body[index + offset] != token[offset]) {
                        continue outer;
                    }
                }
                return index;
            }
            return -1;
        }

        private static byte[] slice(byte[] body, int start, int end) {
            int safeStart = Math.max(0, start);
            int safeEnd = Math.max(safeStart, Math.min(body.length, end));
            byte[] slice = new byte[safeEnd - safeStart];
            System.arraycopy(body, safeStart, slice, 0, slice.length);
            return slice;
        }
    }

    private record PartDisposition(String name, String filename) {
    }
}

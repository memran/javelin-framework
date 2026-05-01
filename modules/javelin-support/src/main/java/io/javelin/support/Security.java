package io.javelin.support;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Base64;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public final class Security {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> RESERVED_WINDOWS_NAMES = reservedWindowsNames();

    private Security() {
    }

    public static boolean constantTimeEquals(CharSequence left, CharSequence right) {
        if (left == null || right == null) {
            return false;
        }
        byte[] leftBytes = left.toString().getBytes(StandardCharsets.UTF_8);
        byte[] rightBytes = right.toString().getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(leftBytes, rightBytes);
    }

    public static String sha256Hex(String value) {
        Objects.requireNonNull(value, "value");
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to hash value", exception);
        }
    }

    public static String randomToken(int byteLength) {
        if (byteLength <= 0) {
            throw new IllegalArgumentException("byteLength must be greater than zero");
        }
        byte[] bytes = new byte[byteLength];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String sanitizeFilename(String value) {
        Objects.requireNonNull(value, "value");
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFKC).trim();
        normalized = normalized.replace('\\', '/');
        int slash = normalized.lastIndexOf('/');
        if (slash >= 0) {
            normalized = normalized.substring(slash + 1);
        }
        normalized = normalized.replaceAll("[\\p{Cntrl}<>:\"|?*]", "-");
        normalized = normalized.replaceAll("\\s+", " ").trim();
        normalized = normalized.replaceAll("^[.\\s]+|[.\\s]+$", "");
        normalized = normalized.replaceAll("-+", "-");
        if (normalized.isBlank()) {
            return "file";
        }

        int dot = normalized.lastIndexOf('.');
        String base = dot > 0 ? normalized.substring(0, dot) : normalized;
        String extension = dot > 0 ? normalized.substring(dot) : "";
        if (RESERVED_WINDOWS_NAMES.contains(base.toUpperCase(Locale.ROOT))) {
            base = "_" + base;
        }
        return base + extension;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            result.append(Character.forDigit((value >>> 4) & 0x0F, 16));
            result.append(Character.forDigit(value & 0x0F, 16));
        }
        return result.toString();
    }

    private static Set<String> reservedWindowsNames() {
        Set<String> names = new HashSet<>();
        names.add("CON");
        names.add("PRN");
        names.add("AUX");
        names.add("NUL");
        for (int index = 1; index <= 9; index++) {
            names.add("COM" + index);
            names.add("LPT" + index);
        }
        return Set.copyOf(names);
    }
}

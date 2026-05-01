package io.javelin.support;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Html {
    private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]*>");

    private Html() {
    }

    public static String escape(String value) {
        Objects.requireNonNull(value, "value");
        StringBuilder result = new StringBuilder(value.length() + 16);
        for (int index = 0; index < value.length(); index++) {
            char ch = value.charAt(index);
            switch (ch) {
                case '&' -> result.append("&amp;");
                case '<' -> result.append("&lt;");
                case '>' -> result.append("&gt;");
                case '"' -> result.append("&quot;");
                case '\'' -> result.append("&#39;");
                default -> result.append(ch);
            }
        }
        return result.toString();
    }

    public static String stripTags(String value) {
        Objects.requireNonNull(value, "value");
        return TAG_PATTERN.matcher(value).replaceAll("");
    }
}

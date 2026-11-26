package io.javelin.mail;

import java.util.regex.Pattern;

/** Explicit email address validation. */
public final class EmailAddress {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private EmailAddress() {
    }

    public static boolean isValid(String value) {
        return value != null && EMAIL.matcher(value).matches();
    }

    public static String requireValid(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid email address: " + value);
        }
        return value;
    }
}

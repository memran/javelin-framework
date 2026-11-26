package io.javelin.providers;

/** Framework version used for provider compatibility checks. */
public final class JavelinVersion {
    public static final String DEFAULT = "0.1.0";

    private JavelinVersion() {
    }

    public static String version() {
        Package pkg = JavelinVersion.class.getPackage();
        String value = pkg == null ? null : pkg.getImplementationVersion();
        return value == null || value.isBlank() ? DEFAULT : value;
    }
}

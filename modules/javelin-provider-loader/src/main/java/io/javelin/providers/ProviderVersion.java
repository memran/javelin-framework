package io.javelin.providers;

/** Minimal numeric version comparison: major.minor.patch, ignoring qualifiers. */
public final class ProviderVersion implements Comparable<ProviderVersion> {
    private final int major;
    private final int minor;
    private final int patch;

    private ProviderVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static ProviderVersion parse(String value) {
        if (value == null || value.isBlank()) {
            return new ProviderVersion(0, 0, 0);
        }
        String numeric = value.trim().replaceFirst("^v", "");
        int dash = numeric.indexOf('-');
        if (dash > 0) {
            numeric = numeric.substring(0, dash);
        }
        String[] parts = numeric.split("\\.");
        int[] numbers = new int[3];
        for (int index = 0; index < Math.min(parts.length, 3); index++) {
            try {
                numbers[index] = Integer.parseInt(parts[index]);
            } catch (NumberFormatException ignored) {
                numbers[index] = 0;
            }
        }
        return new ProviderVersion(numbers[0], numbers[1], numbers[2]);
    }

    public static boolean compatible(String frameworkVersion, String min, String max) {
        ProviderVersion framework = parse(frameworkVersion);
        if (!min.isBlank() && framework.compareTo(parse(min)) < 0) {
            return false;
        }
        return max.isBlank() || framework.compareTo(parse(max)) <= 0;
    }

    @Override
    public int compareTo(ProviderVersion other) {
        if (major != other.major) return Integer.compare(major, other.major);
        if (minor != other.minor) return Integer.compare(minor, other.minor);
        return Integer.compare(patch, other.patch);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}

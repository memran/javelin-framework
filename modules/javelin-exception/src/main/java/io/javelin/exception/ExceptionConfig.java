package io.javelin.exception;

import io.javelin.core.AppProfile;
import io.javelin.core.Config;

import java.nio.file.Path;
import java.util.List;

/** Configuration for centralized error handling. All keys are explicit with documented defaults. */
public record ExceptionConfig(boolean detailed, boolean redact, List<String> redactKeys, Path pagesDirectory) {
    public static final boolean DEFAULT_REDACT = true;
    public static final String DEFAULT_PAGES_DIR = "errors";

    public static ExceptionConfig from(Config config, AppProfile profile) {
        boolean detailed = config.getBoolean("errors.detailed").orElse(profile.isDev());
        boolean redact = config.getBoolean("errors.redact").orElse(DEFAULT_REDACT);
        List<String> keys = config.getStringList("errors.redact_keys");
        Path directory = Path.of(config.getString("errors.pages_dir", DEFAULT_PAGES_DIR));
        return new ExceptionConfig(detailed, redact, List.copyOf(keys), directory);
    }
}

package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AppProfileTest {
    @Test
    void mapsProductionEnvValuesToProd() {
        assertEquals(AppProfile.PROD, AppProfile.parse("prod"));
        assertEquals(AppProfile.PROD, AppProfile.parse("PRODUCTION"));
    }

    @Test
    void treatsOtherValuesAsDev() {
        assertEquals(AppProfile.DEV, AppProfile.parse("dev"));
        assertEquals(AppProfile.DEV, AppProfile.parse("local"));
        assertEquals(AppProfile.DEV, AppProfile.parse(""));
        assertEquals(AppProfile.DEV, AppProfile.parse(null));
    }

    @Test
    void prefersEnvOverConfigAndDefaultsToDev() {
        Config config = new ProfileConfig("prod");
        Env prodEnv = key -> key.equals("APP_ENV") ? Optional.of("dev") : Optional.empty();
        assertEquals(AppProfile.DEV, AppProfile.from(prodEnv, config));

        Env emptyEnv = key -> Optional.empty();
        assertEquals(AppProfile.PROD, AppProfile.from(emptyEnv, config));
        assertEquals(AppProfile.DEV, AppProfile.from(emptyEnv, new ProfileConfig(null)));
    }

    private static final class ProfileConfig implements Config {
        private final String env;

        private ProfileConfig(String env) {
            this.env = env;
        }

        @Override
        public Optional<String> getString(String key) {
            return key.equals("app.env") && env != null ? Optional.of(env) : Optional.empty();
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public List<String> getStringList(String key) {
            return List.of();
        }
    }
}

package io.javelin.exception;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Env;
import io.javelin.core.ExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ExceptionHandlerServiceProviderTest {
    @Test
    void registersConfiguredHandlerWithCustomPages(@TempDir Path dir) throws Exception {
        Files.writeString(dir.resolve("500.html"), "<h1>Deploy page</h1>");
        Map<String, String> values = Map.of(
                "errors.detailed", "false",
                "errors.pages_dir", dir.toString());
        Config config = new MapConfig(values);
        Application app = new Application(config, key -> Optional.empty());

        app.register(new ExceptionHandlerServiceProvider());

        ExceptionHandler handler = app.make(ExceptionHandler.class);
        assertInstanceOf(ConfigurableExceptionHandler.class, handler);
        assertTrue(handler instanceof ConfigurableExceptionHandler);
    }

    @Test
    void configDefaultsToRedactionAndProfileDrivenDetail() {
        ExceptionConfig config = ExceptionConfig.from(new MapConfig(Map.of()), io.javelin.core.AppProfile.PROD);
        assertTrue(config.redact());
        assertTrue(config.pagesDirectory().toString().equals("errors"));
        assertTrue(!config.detailed());
    }

    private record MapConfig(Map<String, String> values) implements Config {
        @Override
        public Optional<String> getString(String key) {
            return Optional.ofNullable(values.get(key));
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return getString(key).map(Integer::parseInt);
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return getString(key).map(Boolean::parseBoolean);
        }

        @Override
        public List<String> getStringList(String key) {
            return List.of();
        }
    }
}

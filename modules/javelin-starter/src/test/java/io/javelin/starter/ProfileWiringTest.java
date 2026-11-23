package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.AppProfile;
import io.javelin.core.Config;
import io.javelin.core.Env;
import io.javelin.core.HttpKernel;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Router;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProfileWiringTest {
    @Test
    void devWiresDetailedErrorsAndNoStoreHeaders() {
        Application app = application("dev");
        HttpKernel kernel = app.make(HttpKernel.class);

        Response ok = kernel.handle(request("/ok"));
        Response error = kernel.handle(request("/boom"));

        assertEquals(AppProfile.DEV, app.profile());
        assertEquals("dev", ok.headers().get("X-Javelin-Profile"));
        assertEquals("no-store", ok.headers().get("Cache-Control"));
        assertTrue(new String(error.body(), StandardCharsets.UTF_8).contains("IllegalStateException"));
    }

    @Test
    void prodUsesGenericErrorsAndNoDevHeaders() {
        Application app = application("prod");
        HttpKernel kernel = app.make(HttpKernel.class);

        Response ok = kernel.handle(request("/ok"));
        Response error = kernel.handle(request("/boom"));

        assertEquals(AppProfile.PROD, app.profile());
        assertFalse(ok.headers().containsKey("X-Javelin-Profile"));
        assertFalse(new String(error.body(), StandardCharsets.UTF_8).contains("IllegalStateException"));
    }

    private static Application application(String envValue) {
        Application app = new Application(new ProfileConfig(), envValue == null
                ? key -> Optional.empty()
                : key -> key.equals("APP_ENV") ? Optional.of(envValue) : Optional.empty());
        app.register(new HttpServerDriverProvider());
        Router router = app.router();
        router.get("/ok", request -> Response.text("ok"));
        router.get("/boom", request -> { throw new IllegalStateException("boom"); });
        app.boot();
        return app;
    }

    private static Request request(String path) {
        return new Request(io.javelin.core.HttpMethod.GET, path,
                java.util.Map.of("Accept", List.of("text/html")),
                java.util.Map.of(), java.util.Map.of(), new byte[0], "127.0.0.1");
    }

    private static final class ProfileConfig implements Config {
        @Override
        public Optional<String> getString(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return key.equals("server.port") ? Optional.of(0) : Optional.empty();
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

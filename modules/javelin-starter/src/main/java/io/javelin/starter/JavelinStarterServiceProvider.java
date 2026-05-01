package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

import java.nio.file.Path;

public final class JavelinStarterServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        Path root = app.has(Path.class) ? app.make(Path.class) : Path.of(System.getProperty("user.dir"));
        Path migrationsDirectory = root.resolve(app.config().getString("database.migrations_dir", "database/migrations"));
        Path uploadsDirectory = root.resolve(app.config().getString("storage.upload_dir", "storage/uploads"));
        Path staticDirectory = root.resolve(app.config().getString("server.static_dir", "public"));
        long maxRequestBytes = app.config().getInt("security.max_request_bytes", 1_048_576);
        app.instance(WorkspaceDefaults.class, new WorkspaceDefaults(root, migrationsDirectory, uploadsDirectory, staticDirectory, maxRequestBytes));
    }

    @Override
    public void boot(Application app) {
        new ValidationRuleLoader().load(app);
    }
}

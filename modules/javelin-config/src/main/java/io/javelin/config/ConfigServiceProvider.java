package io.javelin.config;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

import java.nio.file.Path;

public final class ConfigServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        Path root = Path.of(System.getProperty("user.dir"));
        DotenvEnv env = DotenvEnv.load(root.resolve(".env"));
        app.instance(io.javelin.core.Env.class, env);
        app.instance(io.javelin.core.Config.class, YamlConfig.load(root.resolve("config/app.yaml"), env));
    }
}

package app.providers;

import app.controllers.HomeController;
import app.controllers.UserController;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class AppServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(HomeController.class, HomeController::new);
        app.singleton(UserController.class, UserController::new);
    }

    @Override
    public void boot(Application app) {
        HomeController home = app.make(HomeController.class);
        UserController users = app.make(UserController.class);
        app.router()
                .get("/", home::index)
                .get("/health", home::health)
                .get("/users/{id}", users::show);
    }
}

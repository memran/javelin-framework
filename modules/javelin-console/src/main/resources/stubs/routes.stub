package routes;

import app.controllers.HomeController;
import app.controllers.UserController;
import io.javelin.core.Application;
import static io.javelin.core.Routes.add;
import static io.javelin.core.Routes.get;
import static io.javelin.core.Routes.group;

public final class web {
    private web() {
    }

    public static void register(Application app) {
        HomeController home = app.make(HomeController.class);
        UserController users = app.make(UserController.class);

        add(app.router(), get("/", home::index));
        add(app.router(), get("/health", home::health));
        group(app.router(), "/users",
                usersRoutes -> add(usersRoutes, get("/{id}", users::show)));
    }
}

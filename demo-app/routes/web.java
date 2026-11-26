package routes;

import app.controllers.BackendController;
import app.controllers.HomeController;
import app.controllers.QueueExampleController;
import app.controllers.SecurityExampleController;
import app.controllers.StorageExampleController;
import app.controllers.UserController;
import io.javelin.core.Application;
import io.javelin.core.security.BearerAuthMiddleware;
import io.javelin.core.security.BearerTokenResolver;
import io.javelin.core.security.RequiresAuthenticationMiddleware;
import static io.javelin.core.Routes.add;
import static io.javelin.core.Routes.get;
import static io.javelin.core.Routes.group;
import static io.javelin.core.Routes.post;

public final class web {
    private web() {
    }

    public static void register(Application app) {
        HomeController home = app.make(HomeController.class);
        UserController users = app.make(UserController.class);
        BackendController backends = app.make(BackendController.class);
        SecurityExampleController security = app.make(SecurityExampleController.class);
        StorageExampleController files = app.make(StorageExampleController.class);
        QueueExampleController jobs = app.make(QueueExampleController.class);

        add(app.router(), get("/", home::index));
        add(app.router(), get("/health", home::health));
        add(app.router(), get("/cache/set", backends::cacheSet));
        add(app.router(), get("/cache/get", backends::cacheGet));
        add(app.router(), get("/queue/publish", backends::publish));
        group(app.router(), "/users",
                usersRoutes -> add(usersRoutes, get("/{id}", users::show)));

        // Runnable examples for docs/tutorials/secure-web-app.md
        add(app.router(), get("/demo/session/login", security::loginForm));
        add(app.router(), post("/demo/session/login", security::login));
        add(app.router(), get("/demo/session/me", security::me)
                .middleware(new RequiresAuthenticationMiddleware()));
        add(app.router(), get("/demo/session/logout", security::logout));
        add(app.router(), get("/demo/api/token", security::token));
        add(app.router(), get("/demo/api/me", security::me)
                .middleware(new BearerAuthMiddleware(app.make(BearerTokenResolver.class))));

        // Runnable examples for docs/tutorials/files-and-cache.md
        add(app.router(), get("/demo/files", files::form));
        add(app.router(), post("/demo/files", files::upload));
        add(app.router(), get("/demo/files/download", files::download));
        add(app.router(), get("/demo/cache", files::cache));
        add(app.router(), get("/demo/queue/dispatch", jobs::dispatch));
        add(app.router(), get("/demo/queue/later", jobs::later));
        add(app.router(), get("/demo/queue/status", jobs::status));
    }
}

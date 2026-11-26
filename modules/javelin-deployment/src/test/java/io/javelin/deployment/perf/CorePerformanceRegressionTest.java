package io.javelin.deployment.perf;

import io.javelin.core.Container;
import io.javelin.core.DefaultExceptionHandler;
import io.javelin.core.HttpKernel;
import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Router;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Fast throughput floors for the core routing, container and middleware hot paths. */
final class CorePerformanceRegressionTest {
    private static final PerformanceReport REPORT = new PerformanceReport("core");
    private static final long OPERATIONS = 20_000;

    @AfterAll
    static void publish() {
        REPORT.publish();
    }

    @Test
    void staticRouteLookupDoesNotSlowDownAsRoutesAreAdded() {
        Router router = new Router();
        for (int index = 0; index < 200; index++) {
            router.get("/static/route-" + index, request -> Response.text("ok"));
        }
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request first = request("/static/route-0");
        Request last = request("/static/route-199");
        assertEquals(200, kernel.handle(last).status());

        PerformanceReport.Measurement early = REPORT.measure("core.router.static.first", OPERATIONS, 100_000,
                () -> kernel.handle(first));
        PerformanceReport.Measurement late = REPORT.measure("core.router.static.last", OPERATIONS, 100_000,
                () -> kernel.handle(last));

        assertTrue(early.operationsPerSecond() / late.operationsPerSecond() < 2.5,
                "late static route lookup should not be substantially slower than the first route");
    }

    @Test
    void patternRouteMatchingStaysAboveItsFloor() {
        Router router = new Router();
        router.get("/users/{id}", request -> Response.text(request.path()));
        router.get("/users/{id}/profile", request -> Response.text(request.path()));
        router.get("/orders/{orderId}/items/{itemId}", request -> Response.text(request.path()));
        HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
        Request request = request("/users/42/profile");
        assertEquals("/users/42/profile", new String(kernel.handle(request).body()));

        REPORT.measure("core.router.pattern", OPERATIONS, 50_000, () -> kernel.handle(request));
    }

    @Test
    void containerLookupsStayAboveTheirFloors() {
        Container container = new Container();
        container.instance(InstanceService.class, new InstanceService());
        container.singleton(SingletonService.class, SingletonService::new);
        container.factory(FactoryService.class, FactoryService::new);

        REPORT.measure("core.container.instance", OPERATIONS, 400_000,
                () -> container.make(InstanceService.class));
        REPORT.measure("core.container.singleton", OPERATIONS, 200_000,
                () -> container.make(SingletonService.class));
        REPORT.measure("core.container.factory", OPERATIONS, 200_000,
                () -> container.make(FactoryService.class));
        REPORT.measure("core.container.autowire", OPERATIONS, 30_000,
                () -> container.make(DeepService.class));
    }

    @Test
    void middlewarePipelineStaysAboveItsFloor() {
        Router plain = new Router();
        plain.get("/perf", request -> Response.text("ok"));
        HttpKernel direct = new HttpKernel(plain, new DefaultExceptionHandler());
        Request request = request("/perf");
        assertEquals(200, direct.handle(request).status());
        REPORT.measure("core.kernel.dispatch", OPERATIONS, 100_000, () -> direct.handle(request));

        Router routed = new Router();
        for (int index = 0; index < 5; index++) {
            routed.middleware((incoming, next) -> next.handle(incoming));
        }
        routed.get("/perf", incoming -> Response.text("ok"));
        HttpKernel withMiddleware = new HttpKernel(routed, new DefaultExceptionHandler());
        assertEquals(200, withMiddleware.handle(request).status());
        REPORT.measure("core.middleware.depth5", OPERATIONS, 50_000, () -> withMiddleware.handle(request));
    }

    private static Request request(String path) {
        return new Request(HttpMethod.GET, path, Map.of(), Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static final class InstanceService {
    }

    private static final class SingletonService {
    }

    private static final class FactoryService {
    }

    private static final class LeafService {
    }

    private static final class MiddleService {
        MiddleService(LeafService leaf) {
        }
    }

    private static final class DeepService {
        DeepService(MiddleService middle) {
        }
    }
}

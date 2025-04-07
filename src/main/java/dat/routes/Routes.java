package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final SkiLessonRoute skiLessonRoute = new SkiLessonRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/skilessons", skiLessonRoute.getRoutes());
        };
    }
}

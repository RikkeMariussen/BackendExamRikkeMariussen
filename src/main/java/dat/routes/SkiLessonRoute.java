package dat.routes;

import dat.controllers.impl.SkiLessonController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class SkiLessonRoute {
    private final SkiLessonController skiLessonController = new SkiLessonController();

    protected EndpointGroup getRoutes() {
        return () -> {
            // Task 3: Basic CRUD
            get("/", skiLessonController::readAll);
            get("/{id}", skiLessonController::read);
            post("/", skiLessonController::create);
            put("/{id}", skiLessonController::update);
            delete("/{id}", skiLessonController::delete);
            put("/{lessonId}/instructors/{instructorId}", skiLessonController::addInstructorToSkiLesson);
            post("/populate", skiLessonController::populate);

            // Task 5: Filtering and price summary
            get("/filter/{level}", skiLessonController::getLessonsByCategory);
            get("/instructors/totalprice", skiLessonController::getTotalPriceForLessonByInstructor);
        };
    }
}

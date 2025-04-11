package dat.routes;

import dat.controllers.impl.SkiLessonController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class SkiLessonRoute {
    private final SkiLessonController skiLessonController = new SkiLessonController();

    protected EndpointGroup getRoutes() {
        return () -> {
            // Task 3: Basic CRUD
            get("/", skiLessonController::readAll, Role.ANYONE);
            get("/{id}", skiLessonController::read, Role.ANYONE);
            post("/", skiLessonController::create, Role.ADMIN);
            put("/{id}", skiLessonController::update, Role.ADMIN);
            delete("/{id}", skiLessonController::delete, Role.ADMIN);
            put("/{lessonId}/instructors/{instructorId}", skiLessonController::addInstructorToSkiLesson, Role.ADMIN);
            post("/populate", skiLessonController::populate, Role.ADMIN);

            // Task 5: Filtering and price summary
            get("/filter/{level}", skiLessonController::getLessonsByCategory, Role.ANYONE);
            get("/instructors/totalprice", skiLessonController::getTotalPriceForLessonByInstructor, Role.USER);
        };
    }
}
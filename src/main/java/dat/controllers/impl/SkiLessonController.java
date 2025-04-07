package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.InstructorDAO;
import dat.daos.impl.SkiLessonDAO;
import dat.dtos.InstructorDTO;
import dat.dtos.SkiLessonDTO;
import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.exceptions.ApiException;
import dat.exceptions.Message;
import dat.utils.Populator;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkiLessonController implements IController<SkiLessonDTO, Long> {

    private SkiLessonDAO skiLessonDAO;
    private InstructorDAO instructorDAO;

    public SkiLessonController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.skiLessonDAO = SkiLessonDAO.getInstance(emf);
        this.instructorDAO = InstructorDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        //request - If the ID is not valid a message is printed out in Json
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();

        //entity/dto
        SkiLessonDTO skiLessonDTO = skiLessonDAO.read(id);

        // response
        ctx.res().setStatus(200);
        ctx.json(skiLessonDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<SkiLessonDTO> skiLessonDTOS = skiLessonDAO.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(skiLessonDTOS);
    }

    @Override
    public void create(Context ctx) {
        // request
        SkiLessonDTO jsonRequest = ctx.bodyAsClass(SkiLessonDTO.class);
        // DTO
        SkiLessonDTO skiLessonDTO = skiLessonDAO.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(skiLessonDTO);
    }

    @Override
    public void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        SkiLessonDTO skiLessonDTO = skiLessonDAO.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(skiLessonDTO);
    }

    @Override
    public void delete(Context ctx) {
        //If the ID is not valid a message is printed out in Json
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        skiLessonDAO.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    //Validation - ERROR handling
    @Override
    public boolean validatePrimaryKey(Long id) {
        return skiLessonDAO.validatePrimaryKey(id);
    }

    @Override
    public SkiLessonDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(SkiLessonDTO.class)
                .check(t -> t.getName() != null && !t.getName().isBlank(), "Not a valid lesson name")
                .check(t -> t.getLevel() != null, "Not a valid level")
                .check(t -> t.getPrice() != null && t.getPrice().compareTo(BigDecimal.ZERO) > 0, "Not a valid price")
                .get();
    }

    public void addInstructorToSkiLesson(Context ctx) {
        Long lessonId = Long.valueOf((ctx.pathParam("lessonId")));
        Long instructorId = Long.valueOf((ctx.pathParam("instructorId")));

        try {
            skiLessonDAO.addInstructorToSkiLesson(lessonId, instructorId);
            ctx.status(200).result("Instruktøren blev tilføjet til skilektionen.");
            SkiLessonDTO skiLessonDTO = skiLessonDAO.read(lessonId);
            ctx.json(skiLessonDTO);
        } catch (Exception e) {
            ctx.status(500).result("Der opstod en fejl: " + e.getMessage());
        }
    }

    public void populate(Context ctx) {
        try {
            List<Instructor> instructors = Populator.populateInstructors();
            List<SkiLesson> skiLessons = Populator.populateSkiLessons();

            List<InstructorDTO> instructorDTOS = instructors.stream()
                    .map(instructor -> new InstructorDTO(instructor, true))
                    .toList();

            List<SkiLessonDTO> skiLessonDTOS = skiLessons.stream()
                    .map(lesson -> new SkiLessonDTO(lesson, true))
                    .toList();

            for (SkiLessonDTO skiLessonDTO : skiLessonDTOS) {
                skiLessonDAO.create(skiLessonDTO);
            }

            for (InstructorDTO instructorDTO : instructorDTOS) {
                System.out.println("Creating: " + instructorDTO);
                instructorDAO.create(instructorDTO);
            }

            // Respond with a success message
            ctx.status(200).json(new Message(200, "Database populated successfully with lessons and instructors"));

        } catch (Exception e) {
            // Handle any exceptions during population
            ctx.status(500).json(new Message(500, "Error occurred while populating the database: " + e.getMessage()));
        }
    }

    //5.1
    public void getLessonsByCategory(Context ctx) throws ApiException {
        String level = ctx.pathParam("level");
        List<SkiLessonDTO> skiLessonDTOS = skiLessonDAO.readAll();
        List<SkiLessonDTO> filteredLessons =
                skiLessonDTOS.stream()
                        .filter(lesson -> lesson.getLevel().toString().toLowerCase()
                                .equals(level.toLowerCase()))
                        .collect(Collectors.toList());
        ctx.res().setStatus(200);
        ctx.json(filteredLessons);
    }

    //5.2
    public void getTotalPriceForLessonByInstructor(Context ctx) throws ApiException {
        List<SkiLessonDTO> lessons = skiLessonDAO.readAll();


        Map<Long, BigDecimal> instructorTotals = lessons.stream()
                .filter(l -> l.getInstructorDTO() != null && l.getInstructorDTO().getId() != null)
                .collect(Collectors.groupingBy(
                        l -> l.getInstructorDTO().getId(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                SkiLessonDTO::getPrice,
                                BigDecimal::add
                        )
                ));

        // JSON output format
        List<Map<String, Object>> result = instructorTotals.entrySet().stream()
                .map(entry -> Map.of(
                        "instructorId", entry.getKey(),
                        "totalPrice", (Object) entry.getValue()
                ))
                .collect(Collectors.toList());
        ctx.json(result);
    }
}

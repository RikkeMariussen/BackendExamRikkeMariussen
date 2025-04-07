package dat.daos;

import dat.dtos.SkiLessonDTO;

import java.util.Set;

public interface ISkiLessonInstructorDAO {
    void addInstructorToSkiLesson(Long lessonId, Long instructorId);
    Set<SkiLessonDTO> getSkiLessonsByInstructor(Long instructorId);
}

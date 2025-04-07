package dat.daos.impl;

import dat.daos.IDAO;
import dat.daos.ISkiLessonInstructorDAO;
import dat.dtos.SkiLessonDTO;
import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SkiLessonDAO implements IDAO<SkiLessonDTO, Long>, ISkiLessonInstructorDAO {

    private static SkiLessonDAO instance;
    private static EntityManagerFactory emf;

    public static SkiLessonDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new SkiLessonDAO();
        }
        return instance;
    }

    @Override
    public SkiLessonDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            SkiLesson skiLesson = em.find(SkiLesson.class, id);
            return new SkiLessonDTO(skiLesson);
        }
    }

    @Override
    public List<SkiLessonDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<SkiLesson> skiLessons = em.createQuery("SELECT s FROM SkiLesson s", SkiLesson.class).getResultList();
            return skiLessons.stream()
                    .map(SkiLessonDTO::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ApiException(500, "Error finding list of ski lessons: " + e.getMessage());
        }
    }

    @Override
    public SkiLessonDTO create(SkiLessonDTO skiLessonDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson skiLesson = skiLessonDTO.toEntity();
            em.persist(skiLesson);
            em.getTransaction().commit();
            return new SkiLessonDTO(skiLesson);
        } catch (Exception e) {
            throw new ApiException(401, "Error creating skilesson: " + skiLessonDTO);
        }
    }

    @Override
    public SkiLessonDTO update(Long id, SkiLessonDTO skiLessonDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson s = em.find(SkiLesson.class, id);
            s.setName(skiLessonDTO.getName());
            s.setStartTime(skiLessonDTO.getStartTime());
            s.setEndTime(skiLessonDTO.getEndTime());
            s.setPrice(skiLessonDTO.getPrice());
            s.setLongitude(skiLessonDTO.getLongitude());
            s.setLatitude(skiLessonDTO.getLatitude());
            s.setInstructor(s.getInstructor());
            SkiLesson mergedSkiLesson = em.merge(s);
            em.getTransaction().commit();
            return mergedSkiLesson != null ? new SkiLessonDTO(mergedSkiLesson) : null;
        } catch (Exception e) {
            throw new ApiException(500, "Error updating skilesson: " + skiLessonDTO);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                SkiLesson skiLesson = em.find(SkiLesson.class, id);
                if (skiLesson != null) {
                    em.remove(skiLesson);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error deleting ski lesson wih id: " + id);
            }
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            SkiLesson skiLesson = em.find(SkiLesson.class, id);
            return skiLesson != null;
        }
    }

    @Override
    public void addInstructorToSkiLesson(Long lessonId, Long instructorId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            SkiLesson lesson = em.find(SkiLesson.class, lessonId);
            Instructor instructor = em.find(Instructor.class, instructorId);

            if (lesson == null || instructor == null) {
                throw new IllegalArgumentException("The instructor or lesson was not found");
            }

            instructor.addLesson(lesson);

            em.merge(instructor);
            em.merge(lesson);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApiException(500, "Error adding instructor: " + instructorId);
        }
    }

    @Override
    public Set<SkiLessonDTO> getSkiLessonsByInstructor(Long instructorId) {
        EntityManager em = emf.createEntityManager();
        try {
            Instructor instructor = em.find(Instructor.class, instructorId);

            if (instructor == null) {
                throw new IllegalArgumentException("Instructor with id: " + instructorId + " was not found.");
            }

            return instructor.getLessons().stream()
                    .map(SkiLessonDTO::new)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ApiException(500, "Error getting ski lessons by instructor with id: " + instructorId);
        }
    }

}
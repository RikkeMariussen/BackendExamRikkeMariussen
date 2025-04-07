package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.InstructorDTO;
import dat.entities.Instructor;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class InstructorDAO implements IDAO<InstructorDTO, Long> {

    private static InstructorDAO instance;
    private static EntityManagerFactory emf;

    public static InstructorDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new InstructorDAO();
        }
        return instance;
    }

    @Override
    public InstructorDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Instructor instructor = em.find(Instructor.class, id);
            return new InstructorDTO(instructor);
        }
    }

    @Override
    public List<InstructorDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Instructor> instructors = em.createQuery("SELECT s FROM Instructor s", Instructor.class).getResultList();
            return instructors.stream()
                    .map(InstructorDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApiException(500, "Error finding list of instructors: " + e.getMessage());
        }
    }

    @Override
    public InstructorDTO create(InstructorDTO instructorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = instructorDTO.toEntity();
            em.persist(instructor);
            em.getTransaction().commit();
            return new InstructorDTO(instructor);
        } catch (Exception e) {
            throw new ApiException(401, "Error creating instructor: " + instructorDTO);
        }
    }

    @Override
    public InstructorDTO update(Long id, InstructorDTO instructorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = instructorDTO.toEntity();
            Instructor updatedInstructor = em.merge(instructor);
            em.getTransaction().commit();
            InstructorDTO updatedInstructorDTO = new InstructorDTO(updatedInstructor);
            return updatedInstructorDTO;
        } catch (Exception e) {
            throw new ApiException(500, "Error updating instructor: " + instructorDTO);
        }
    }


    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Instructor instructor = em.find(Instructor.class, id);
                if (instructor != null) {
                    em.remove(instructor);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error deleting instructor wih id: " + id);
            }
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Instructor instructor = em.find(Instructor.class, id);
            return instructor != null;
        }
    }
}

package dat.utils;

import dat.entities.*;
import dat.enums.Level;
//import dat.security.entities.User;
//import dat.security.entities.Role;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Populator {
    //Den her tager sig af brugere (security)
   /* public static UserDTO[] populateUsers(EntityManagerFactory emf) {
        User user, admin;
        Role userRole, adminRole;

        user = new User("usertest", "user123");
        admin = new User("admintest", "admin123");
        userRole = new Role("USER");
        adminRole = new Role("ADMIN");
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }
        UserDTO userDTO = new UserDTO(user.getUsername(), "user123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "admin123");
        return new UserDTO[]{userDTO, adminDTO};
    }*/

    //2.5 Populator
    public static List<Instructor> populateInstructors() {
        List<Instructor> instructors = new ArrayList<>();

        Instructor i1 = Instructor.builder()
                .firstName("Lars")
                .lastName("Hansen")
                .email("lars@skiparadiset.dk")
                .phone(48567685)
                .yearsOfExperience(10)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i1);

        Instructor i2 = Instructor.builder()
                .firstName("Mette")
                .lastName("Jensen")
                .email("mette@skiparadiset.dk")
                .phone(48567689)
                .yearsOfExperience(6)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i2);

        Instructor i3 = Instructor.builder()
                .firstName("Anders")
                .lastName("Møller")
                .email("anders@skiparadiset.dk")
                .phone(48567654)
                .yearsOfExperience(8)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i3);

        Instructor i4 = Instructor.builder()
                .firstName("Sara")
                .lastName("Nielsen")
                .email("sara@skiparadiset.dk")
                .phone(48567663)
                .yearsOfExperience(5)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i4);

        Instructor i5 = Instructor.builder()
                .firstName("Frederik")
                .lastName("Olsen")
                .email("frederik@skiparadiset.dk")
                .phone(48567623)
                .yearsOfExperience(12)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i5);

        Instructor i6 = Instructor.builder()
                .firstName("Nina")
                .lastName("Thomsen")
                .email("nina@skiparadiset.dk")
                .phone(48567612)
                .yearsOfExperience(7)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i6);

        Instructor i7 = Instructor.builder()
                .firstName("Jonas")
                .lastName("Christensen")
                .email("jonas@skiparadiset.dk")
                .phone(48567623)
                .yearsOfExperience(4)
                .lessons(new HashSet<>())
                .build();
        instructors.add(i7);
        return instructors;
    }

    public static List<SkiLesson> populateSkiLessons() {
        List<SkiLesson> lessons = new ArrayList<>();
        SkiLesson l1 = SkiLesson.builder()
                .name("Morgentræning")
                .price(BigDecimal.valueOf(100))
                .startTime(LocalDateTime.now().plusDays(1).minusHours(1))
                .endTime(LocalDateTime.now().plusDays(2))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.ADVANCED)
                .build();
        lessons.add(l1);

        SkiLesson l2 = SkiLesson.builder()
                .name("Morgentræning")
                .price(BigDecimal.valueOf(100))
                .startTime(LocalDateTime.now().plusDays(1).minusHours(2))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.BEGINNER)
                .build();
        lessons.add(l2);

        SkiLesson l3 = SkiLesson.builder()
                .name("Freeride teknik")
                .price(BigDecimal.valueOf(500))
                .startTime(LocalDateTime.now().plusDays(1).minusHours(3))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(3))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.ADVANCED)
                .build();
        lessons.add(l3);

        SkiLesson l4 = SkiLesson.builder()
                .name("Begynderhold")
                .price(BigDecimal.valueOf(100))
                .startTime(LocalDateTime.now().plusDays(2).minusHours(1))
                .endTime(LocalDateTime.now().plusDays(2).plusHours(2))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.BEGINNER)
                .build();
        lessons.add(l4);

        SkiLesson l5 = SkiLesson.builder()
                .name("Videregående")
                .price(BigDecimal.valueOf(250))
                .startTime(LocalDateTime.now().plusDays(3).plusHours(1))
                .endTime(LocalDateTime.now().plusDays(3).plusHours(5))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.INTERMEDIATE)
                .build();
        lessons.add(l5);

        SkiLesson l6 = SkiLesson.builder()
                .name("Eftermiddagslektion")
                .price(BigDecimal.valueOf(200))
                .startTime(LocalDateTime.now().plusDays(1).plusHours(5))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(8))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.BEGINNER)
                .build();
        lessons.add(l6);

        SkiLesson l7 = SkiLesson.builder()
                .name("Sort Bakke")
                .price(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.now().plusDays(4).minusHours(1))
                .endTime(LocalDateTime.now().plusDays(4).plusHours(2))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.ADVANCED)
                .build();
        lessons.add(l7);

        SkiLesson l8 = SkiLesson.builder()
                .name("Børn 2")
                .price(BigDecimal.valueOf(100))
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(3))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.INTERMEDIATE)
                .build();
        lessons.add(l8);

        SkiLesson l9 = SkiLesson.builder()
                .name("Teenager Øvet")
                .price(BigDecimal.valueOf(200))
                .startTime(LocalDateTime.now().plusDays(5).plusHours(4))
                .endTime(LocalDateTime.now().plusDays(5).plusHours(8))
                .longitude(5.32800000)
                .latitude(60.39200000)
                .level(Level.ADVANCED)
                .build();
        lessons.add(l9);

        return lessons;
    }
}

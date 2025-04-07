package dat.controllers.impl;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.entities.Instructor;
import dat.entities.SkiLesson;
import dat.utils.Populator;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

class SkiLessonControllerTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7075/api";
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

    @BeforeAll
    static void setUpAll() {
        app = ApplicationConfig.startServer(7075);
    }

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<Instructor> instructors = Populator.populateInstructors();
            List<SkiLesson> skiLessons = Populator.populateSkiLessons();

            instructors.forEach(em::persist);
            skiLessons.forEach(em::persist);

            skiLessons.get(5).setInstructor(instructors.get(0)); // Lektion 1 får Instruktør 1
            skiLessons.get(6).setInstructor(instructors.get(0)); // Lektion 2 får Instruktør 1

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RestAssured.baseURI = "http://localhost:7075/api";
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = HibernateConfig.getEntityManagerFactoryForTest().createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM SkiLesson").executeUpdate();
            em.createQuery("DELETE FROM Instructor").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    @DisplayName("Test getting lesson by id")
    void lessonByIdTest() {
        given()
                .when()
                .get("/skilessons/2")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("name", is("Morgentræning"));
    }

    @Test
    @DisplayName("Test creating a ski lesson")
    void createLessonTest() {
        String newLessonJson = "{ \"name\": \"Sen Eftermiddags lektion\", \"level\": \"INTERMEDIATE\", \"price\": 100 }";

        given()
                .contentType("application/json")
                .body(newLessonJson)
                .when()
                .post("/skilessons")
                .then()
                .statusCode(201)
                .body("name", equalTo("Sen Eftermiddags lektion"))
                .body("level", equalTo("INTERMEDIATE"))
                .body("price", equalTo(100));
    }

    @Test
    @DisplayName("Test updating a ski lesson")
    void updateSkiLessonTest() {
        String updatedLessonJson = "{ \"name\": \"Eftermiddags lektion for begyndere\", \"level\": \"BEGINNER\", \"price\": 150 }";

        given()
                .contentType("application/json")
                .body(updatedLessonJson)
                .when()
                .put("/skilessons/6")
                .then()
                .statusCode(200)
                .body("id", equalTo(6))
                .body("name", equalTo("Eftermiddags lektion for begyndere"))
                .body("level", equalTo("BEGINNER"))
                .body("price", equalTo(150));
    }

    @Test
    @DisplayName("Test deleting a ski lesson")
    void deleteLessonTest() {
        given()
                .when()
                .delete("/skilessons/7")
                .then()
                .statusCode(204);
    }


    @Test
    @DisplayName("Test adding an instructor to a ski lesson")
    void addInstructorToLessonTest() {
        given()
                .when()
                .put("/skilessons/3/instructors/2")
                .then()
                .statusCode(200)
                .body("id", equalTo(3))
                .body("instructorDTO.id", equalTo(2));
    }

    @Test
    @DisplayName("Test filtering ski lessons by level")
    void filterLessonsByLevelTest() {
        given()
                .when()
                .get("/skilessons/filter/beginner")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("[2].level", equalTo("BEGINNER"));
    }

    @Test
    @DisplayName("Test total price for lessons by instructor")
    void totalPriceForLessonsByInstructorTest() {
        given()
                .when()
                .get("/skilessons/instructors/totalprice")
                .then()
                .statusCode(200)
                .body("totalPrice[0]", equalTo(600.0F));
    }

}
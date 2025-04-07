package dat.dtos;

import dat.entities.Instructor;
import dat.entities.SkiLesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InstructorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
    private Set<SkiLessonDTO> skiLessonDTO;

    //TODO: is this redundant?
    public InstructorDTO(Instructor instructor) {
        this.id = instructor.getId();
        this.firstName = instructor.getFirstName();
        this.lastName = instructor.getLastName();
        this.email = instructor.getEmail();
        this.phone = instructor.getPhone();
        this.yearsOfExperience = instructor.getYearsOfExperience();
        if(instructor.getLessons() != null) {
            skiLessonDTO = new HashSet<>();
            for (SkiLesson lesson : instructor.getLessons()) {
                SkiLessonDTO skiLessonDTO = new SkiLessonDTO(lesson, false);
                this.skiLessonDTO.add(skiLessonDTO);
            }

        }
    }
    public InstructorDTO(Instructor instructor, boolean includeSkiLessons) {
        this.id = instructor.getId();
        this.firstName = instructor.getFirstName();
        this.lastName = instructor.getLastName();
        this.email = instructor.getEmail();
        this.phone = instructor.getPhone();
        this.yearsOfExperience = instructor.getYearsOfExperience();
        if(includeSkiLessons && instructor.getLessons() != null) {
            skiLessonDTO = new HashSet<>();
            for (SkiLesson lesson : instructor.getLessons()) {
                this.skiLessonDTO.add(new SkiLessonDTO(lesson, false));
            }
        }
    }

    public Instructor toEntity() {
        Instructor instructor = Instructor.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .yearsOfExperience(yearsOfExperience)
                .lessons(new HashSet<>())
                .build();
        return instructor;
    }
}

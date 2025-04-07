package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id", nullable = false, unique = true)
    private Long id;

    @Setter
    @Column(name="first_name")
    private String firstName;

    @Setter
    @Column(name="last_name")
    private String lastName;

    @Setter
    private String email;

    @Setter
    private int phone;

    @Setter
    @Column(name="years_of_experience")
    private int yearsOfExperience;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<SkiLesson> lessons = new HashSet<>();

    // Bi-directional relationship
    public void addLesson(SkiLesson lesson) {
        if(lesson != null) {
            lessons.add(lesson);
            lesson.setInstructor(this);
        }
    }

    public void removeLesson(SkiLesson lesson){
        if(lesson != null) {
            lessons.remove(lesson);
            lesson.setInstructor(null);
        }
    }
}

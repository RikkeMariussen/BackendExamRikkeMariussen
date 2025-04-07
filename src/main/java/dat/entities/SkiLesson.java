package dat.entities;

import dat.enums.Level;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ski_lesson")
public class SkiLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ski_lesson_id", nullable = false, unique = true)
    private Long id;

    @Setter
    @Column(name = "ski_lesson_name", nullable = false)
    private String name;

    @Setter
    private BigDecimal price;

    @Column(name = "start_time")
    @Setter
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @Setter
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Setter
    private Level level;

    @Setter
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Setter
    private double longitude;

    @Setter
    private double latitude;
}

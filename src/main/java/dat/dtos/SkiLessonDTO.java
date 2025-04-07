package dat.dtos;

import dat.entities.SkiLesson;
import dat.enums.Level;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SkiLessonDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Level level;
    private InstructorDTO instructorDTO;
    private double longitude;
    private double latitude;


    //TODO: is this redundant?
    public SkiLessonDTO(SkiLesson skiLesson) {
        this.id = skiLesson.getId();
        this.name = skiLesson.getName();
        this.price = skiLesson.getPrice();
        this.startTime = skiLesson.getStartTime();
        this.endTime = skiLesson.getEndTime();
        this.level = skiLesson.getLevel();
        this.longitude = skiLesson.getLongitude();
        this.latitude = skiLesson.getLatitude();
        if(skiLesson.getInstructor() != null) {
            this.instructorDTO = new InstructorDTO(skiLesson.getInstructor());
        }
    }
    public SkiLessonDTO(SkiLesson skiLesson, boolean includeInstructor) {
        this.id = skiLesson.getId();
        this.name = skiLesson.getName();
        this.price = skiLesson.getPrice();
        this.startTime = skiLesson.getStartTime();
        this.endTime = skiLesson.getEndTime();
        this.level = skiLesson.getLevel();
        this.longitude = skiLesson.getLongitude();
        this.latitude = skiLesson.getLatitude();
        if(includeInstructor && skiLesson.getInstructor() != null) {
            this.instructorDTO = new InstructorDTO(skiLesson.getInstructor());
        }
    }

    public SkiLesson toEntity() {
        SkiLesson skiLesson = SkiLesson.builder()
                .name(name)
                .price(price)
                .startTime(startTime)
                .endTime(endTime)
                .level(level)
                .instructor(instructorDTO != null? instructorDTO.toEntity() : null)
                .longitude(longitude)
                .latitude(latitude)
                .build();
        return skiLesson;
    }

}

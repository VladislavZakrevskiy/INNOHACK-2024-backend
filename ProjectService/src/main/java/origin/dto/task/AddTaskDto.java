package origin.dto.task;

import lombok.*;
import origin.model.status.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddTaskDto {
    private String name;
    private String description;
    private LocalDateTime deadlineDate;
}

package origin.dto.task;

import lombok.*;
import origin.model.status.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddTaskDto {
    private String name;
    private String description;
}

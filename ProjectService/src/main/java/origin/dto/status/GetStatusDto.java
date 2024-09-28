package origin.dto.status;

import jakarta.persistence.*;
import lombok.*;
import origin.dto.task.GetTaskDto;
import origin.model.space.Space;
import origin.model.task.Task;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetStatusDto {
    private Long id;

    private String name;

    private List<GetTaskDto> tasks;

}

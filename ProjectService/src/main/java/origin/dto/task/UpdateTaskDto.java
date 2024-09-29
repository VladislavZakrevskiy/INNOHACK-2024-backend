package origin.dto.task;

import lombok.*;
import origin.dto.user.ProfileUserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateTaskDto {

    private String title;

    private String description;

    private Long ownerId;

    private Long executorId;

    private LocalDateTime deadlineDate;

    private List<String> image;

    private String place;

    private String checkpoint;

    private List<String> labels;

    private Long statusId;

}

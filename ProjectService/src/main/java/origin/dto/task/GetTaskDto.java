package origin.dto.task;

import jakarta.persistence.ElementCollection;
import lombok.*;
import origin.dto.user.ProfileUserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetTaskDto {
    private Long id;

    private String title;

    private String description;

    private ProfileUserDto owner;

    private ProfileUserDto executor;

    private LocalDateTime createDate;

    private LocalDateTime deadlineDate;

    private List<String> image;

    private String place;

    private String checkpoint;

    private List<String> labels;

    private Long projectId;

    private Long statusId;

    private Long spaceId;

}

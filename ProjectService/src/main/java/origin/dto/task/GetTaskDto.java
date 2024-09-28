package origin.dto.task;

import lombok.*;
import origin.dto.user.ProfileUserDto;

import java.time.LocalDateTime;

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

}

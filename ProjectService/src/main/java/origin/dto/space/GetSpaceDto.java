package origin.dto.space;

import lombok.*;
import origin.dto.status.GetStatusDto;
import origin.dto.user.ProfileUserDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetSpaceDto {
    private Long id;

    private String name;

    private String description;

    private ProfileUserDto owner;

    private List<ProfileUserDto> members;

    private List<GetStatusDto> statuses;

    private String image;
}

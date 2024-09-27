package origin.dto.project;

import jakarta.persistence.*;
import lombok.*;
import origin.dto.space.GetSpaceDto;
import origin.dto.user.ProfileUserDto;
import origin.model.space.Space;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetProjectDto {
    private Long id;

    private String name;

    private ProfileUserDto owner;

    private List<ProfileUserDto> members;

    private List<GetSpaceDto> spaces;
}

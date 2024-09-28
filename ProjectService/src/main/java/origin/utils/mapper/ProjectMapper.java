package origin.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import origin.client.UserClient;
import origin.dto.project.GetProjectDto;
import origin.model.project.Project;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapper {
    private final UserClient userClient;
    private final SpaceMapper spaceMapper;

    public GetProjectDto toDto(Project e) {
        if ( e == null ) {
            return null;
        }


        GetProjectDto getProjectDto = new GetProjectDto();
        getProjectDto.setId(e.getId());
        getProjectDto.setName(e.getName());
        getProjectDto.setSpaces(e.getSpaces().stream().map(spaceMapper::toDto).collect(Collectors.toList()));
        getProjectDto.setOwner(userClient.getUserById(e.getOwnerId()));
        getProjectDto.setMembers(e.getMembersId().stream().map(userClient::getUserById).collect(Collectors.toList()));
        getProjectDto.setImage(e.getImage());

        return getProjectDto;
    }
}

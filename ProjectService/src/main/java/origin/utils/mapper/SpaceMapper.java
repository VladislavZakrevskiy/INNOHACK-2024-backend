package origin.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import origin.client.UserClient;
import origin.dto.project.GetProjectDto;
import origin.dto.space.GetSpaceDto;
import origin.model.project.Project;
import origin.model.space.Space;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpaceMapper {
    private final UserClient userClient;
    private final StatusMapper statusMapper;

    public GetSpaceDto toDto(Space e) {
        if ( e == null ) {
            return null;
        }


        GetSpaceDto getSpaceDto = new GetSpaceDto();
        getSpaceDto.setId(e.getId());
        getSpaceDto.setName(e.getName());
        getSpaceDto.setDescription(e.getDescription());
        getSpaceDto.setOwner(userClient.getUserById(e.getOwnerId()));
        getSpaceDto.setMembers(e.getMembersId().stream().map(userClient::getUserById).collect(Collectors.toList()));
        getSpaceDto.setStatuses(e.getStatus().stream().map(statusMapper::toDto).collect(Collectors.toList()));
        getSpaceDto.setImage(e.getImage());

        return getSpaceDto;
    }
}

package origin.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import origin.client.UserClient;
import origin.dto.space.GetSpaceDto;
import origin.dto.status.GetStatusDto;
import origin.model.space.Space;
import origin.model.status.Status;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatusMapper {
    private final UserClient userClient;
    private final TaskMapper taskMapper;

    public GetStatusDto toDto(Status e) {
        if ( e == null ) {
            return null;
        }

        GetStatusDto getStatusDto = new GetStatusDto();
        getStatusDto.setId(e.getId());
        getStatusDto.setName(e.getName());
        getStatusDto.setTasks(e.getTasks().stream().map(taskMapper::toDto).collect(Collectors.toList()));


        return getStatusDto;
    }
}

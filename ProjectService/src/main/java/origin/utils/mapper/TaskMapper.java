package origin.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import origin.client.UserClient;
import origin.dto.status.GetStatusDto;
import origin.dto.task.GetTaskDto;
import origin.model.status.Status;
import origin.model.task.Task;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserClient userClient;

    public GetTaskDto toDto(Task e) {
        if ( e == null ) {
            return null;
        }

        GetTaskDto getTaskDto = new GetTaskDto();
        getTaskDto.setId(e.getId());
        getTaskDto.setTitle(e.getTitle());
        getTaskDto.setDescription(e.getDescription());
        getTaskDto.setOwner(userClient.getUserById(e.getOwnerId()));
        getTaskDto.setExecutor(userClient.getUserById(e.getExecutorId()));
        getTaskDto.setCreateDate(e.getCreateDate());
        getTaskDto.setDeadlineDate(e.getDeadlineDate());
        getTaskDto.setProjectId(e.getStatus().getSpace().getProject().getId());
        getTaskDto.setSpaceId(e.getStatus().getSpace().getId());
        getTaskDto.setStatusId(e.getStatus().getId());
        return getTaskDto;
    }
}

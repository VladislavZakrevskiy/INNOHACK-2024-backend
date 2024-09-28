package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import origin.client.UserClient;
import origin.dto.status.AddStatusDto;
import origin.dto.status.GetStatusDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.service.ProjectService;
import origin.service.SpaceService;
import origin.service.StatusService;
import origin.utils.mapper.StatusMapper;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "StatusController")
public class StatusController {

    private final UserClient userClient;
    private final StatusService statusService;
    private final StatusMapper statusMapper;
    private final ProjectService projectService;
    private final SpaceService spaceService;

    @PostMapping("/{projectId}/space/{spaceId}/status")
    public GetStatusDto addNewStatusInSpace(@PathVariable long projectId, @PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername,
                                            @RequestBody AddStatusDto addStatusDto){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsMember(project, participantUserId);

        Space space = spaceService.getById(spaceId);

        Status status = new Status();
        status.setSpace(space);
        status.setName(addStatusDto.getName());
        status.setTasks(new ArrayList<>());
        statusService.save(status);
        return statusMapper.toDto(status);
    }

    @PutMapping("/status/{statusId}")
    public GetStatusDto updateStatusById(@PathVariable long statusId, @RequestHeader(value = "X-Username") String participantUsername, @RequestBody GetStatusDto statusDto) {
        Status status = statusService.getById(statusId);
        Space space = spaceService.getById(status.getSpace().getId());
        Project project = projectService.getById(space.getProject().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        statusService.validateUserIsOwner(space, project, participantUserId);

        return statusMapper.toDto(statusService.update(statusId, statusDto));
    }

    @DeleteMapping("/status/{statusId}")
    public GetStatusDto deleteStatusById(@PathVariable long statusId, @RequestHeader(value = "X-Username") String participantUsername) {
        Status status = statusService.getById(statusId);
        GetStatusDto getStatusDto = statusMapper.toDto(status);

        Space space = spaceService.getById(status.getSpace().getId());
        Project project = projectService.getById(space.getProject().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        statusService.validateUserIsOwner(space, project, participantUserId);

        statusService.deleteById(statusId);

        return getStatusDto;
    }
}

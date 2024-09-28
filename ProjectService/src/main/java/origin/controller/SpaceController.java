package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import origin.client.UserClient;
import origin.dto.space.AddSpaceDto;
import origin.dto.space.GetSpaceDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.service.ProjectService;
import origin.service.SpaceService;
import origin.service.StatusService;
import origin.utils.exception.ApiException;
import origin.utils.mapper.SpaceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "SpaceController")
public class SpaceController {

    private final ProjectService projectService;
    private final UserClient userClient;
    private final SpaceMapper spaceMapper;
    private final StatusService statusService;
    private final SpaceService spaceService;


    @GetMapping("/{projectId}/space")
    public List<GetSpaceDto> getAllSpace(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();

        projectService.validateUserIsMember(project, participantUserId);

        return project.getSpaces().stream().map(spaceMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{projectId}/space/{spaceId}")
    public GetSpaceDto getDefiniteSpace(@PathVariable long projectId, @PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsMember(project, participantUserId);
        return spaceMapper.toDto(spaceService.getById(spaceId));
    }

    @PutMapping("/space/{spaceId}")
    public GetSpaceDto updateSpaceById(@PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername, @RequestBody GetSpaceDto getSpaceDto) {
        Status status = statusService.getById(spaceId);
        Space space = spaceService.getById(status.getSpace().getId());
        Project project = projectService.getById(status.getSpace().getProject().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
//        spaceService.validateUserIsOwner(space, project, participantUserId);

        return spaceMapper.toDto(spaceService.update(spaceId, getSpaceDto));
    }

    @DeleteMapping("/space/{spaceId}")
    public GetSpaceDto deleteSpaceById(@PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername) {
        Space space = spaceService.getById(spaceId);
        GetSpaceDto getSpaceDto = spaceMapper.toDto(space);
        Project project = projectService.getById(space.getProject().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        spaceService.validateUserIsOwner(space, project, participantUserId);

        spaceService.deleteById(spaceId);

        return getSpaceDto;
    }

    @PostMapping("/{projectId}/space")
    public GetSpaceDto addNewSpace(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername,
                                   @RequestBody AddSpaceDto addSpaceDto){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();

        projectService.validateUserIsMember(project, participantUserId);
        projectService.validateUserIsOwner(project, participantUserId);

        Space space = new Space();
        space.setName(addSpaceDto.getName());
        space.setDescription(addSpaceDto.getDescription());
        space.setMembersId(new ArrayList<>());
        space.setOwnerId(participantUserId);
        space.setStatus(new ArrayList<>());
        space.setProject(project);

        return spaceMapper.toDto(spaceService.save(space));
    }

    @PostMapping("/{projectId}/space/{spaceId}")
    public GetSpaceDto addNewMemberInSpace(@PathVariable long projectId, @PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername,
                                           @RequestParam(required = true) String usernameToBeAdded){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        Long usernameToBaAddedId = userClient.getUserByUsername(usernameToBeAdded).getId();

        projectService.validateUserIsMember(project, participantUserId);
        projectService.validateUserIsMember(project, userClient.getUserByUsername(usernameToBeAdded).getId());
        projectService.validateUserIsOwner(project, participantUserId);


        Space space = spaceService.getById(spaceId);
        if(space.getMembersId().contains(usernameToBaAddedId)){
            throw new ApiException("Пользователь уже добавлен в проект", HttpStatus.BAD_REQUEST);
        }
        space.getMembersId().add(usernameToBaAddedId);
        return spaceMapper.toDto(spaceService.save(space));
    }
}

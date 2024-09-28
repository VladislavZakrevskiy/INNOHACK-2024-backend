package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import origin.client.UserClient;
import origin.dto.project.AddProjectDto;
import origin.dto.project.GetProjectDto;
import origin.dto.space.AddSpaceDto;
import origin.dto.space.GetSpaceDto;
import origin.dto.status.AddStatusDto;
import origin.dto.task.AddTaskDto;
import origin.dto.task.GetTaskDto;
import origin.dto.user.ProfileUserDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.model.task.Task;
import origin.service.ProjectService;
import origin.service.SpaceService;
import origin.service.StatusService;
import origin.service.TaskService;
import origin.utils.exception.ApiException;
import origin.utils.mapper.ProjectMapper;
import origin.utils.mapper.SpaceMapper;
import origin.utils.mapper.StatusMapper;
import origin.utils.mapper.TaskMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "ProjectService")
public class MainController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserClient userClient;
    private final SpaceMapper spaceMapper;
    private final StatusService statusService;
    private final SpaceService spaceService;
    private final StatusMapper statusMapper;
    private final TaskMapper taskMapper;
    private final TaskService taskService;

    @GetMapping
    public List<GetProjectDto> getAllProjects(@RequestHeader(value = "X-Username") String username) {
        return projectService.findAllByMemberId(userClient.getUserByUsername(username).getId()).stream().map(projectMapper::toDto).collect(Collectors.toList());
    }


    @PostMapping
    public GetProjectDto createProject(@RequestHeader(value = "X-Username") String usernameOwner,
                                       @RequestBody AddProjectDto addProjectDto){
        ProfileUserDto profileUserDto = userClient.getUserByUsername(usernameOwner);
        Project project = new Project();
        project.setName(addProjectDto.getName());
        project.setMembersId(Collections.singletonList(profileUserDto.getId()));
        project.setOwnerId(profileUserDto.getId());
        project.setSpaces(new ArrayList<>());

        return projectMapper.toDto(projectService.save(project));
    }

    @GetMapping("/{projectId}")
    public GetProjectDto getDefiniteProject(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsMember(project, participantUserId);
        return projectMapper.toDto(project);
    }

    @PostMapping("/{projectId}")
    public GetProjectDto addNewMember(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername,
                                       @RequestParam(required = true) String usernameToBeAdded){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();

        projectService.validateUserIsMember(project, participantUserId);
        projectService.validateUserIsOwner(project, participantUserId);

        Long forAddedUserId = userClient.getUserByUsername(usernameToBeAdded).getId();

        if(project.getMembersId().contains(forAddedUserId)){
            throw new ApiException("Пользователь уже добавлен в проект", HttpStatus.BAD_REQUEST);
        }

        project.getMembersId().add(forAddedUserId);
        return projectMapper.toDto(projectService.save(project));
    }




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

        projectService.validateUserIsMember(project, participantUserId);
        projectService.validateUserIsMember(project, userClient.getUserByUsername(usernameToBeAdded).getId());
        projectService.validateUserIsOwner(project, participantUserId);

        Space space = spaceService.getById(spaceId);
        space.getMembersId().add(userClient.getUserByUsername(usernameToBeAdded).getId());
        return spaceMapper.toDto(spaceService.save(space));
    }

    @PostMapping("/{projectId}/space/{spaceId}/status")
    public GetSpaceDto addNewMemberInSpace(@PathVariable long projectId, @PathVariable long spaceId, @RequestHeader(value = "X-Username") String participantUsername,
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
        return spaceMapper.toDto(space);
    }



    @PostMapping("/{projectId}/space/{spaceId}/status/{statusId}/task")
    public GetTaskDto createTask(@PathVariable long projectId, @PathVariable long spaceId,
                                 @PathVariable long statusId,
                                 @RequestHeader(value = "X-Username") String participantUsername,
                                 @RequestBody AddTaskDto addTaskDto){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsMember(project, participantUserId);
        Space space = spaceService.getById(spaceId);
        spaceService.validateUserIsMember(space, participantUserId);
        Status status = statusService.getById(statusId);
        Task task = new Task();
        task.setTitle(addTaskDto.getName());
        task.setDescription(addTaskDto.getDescription());
        task.setStatus(status);
        task.setOwnerId(participantUserId);
        task.setExecutorId(participantUserId);
        return taskMapper.toDto(taskService.save(task));
    }



}

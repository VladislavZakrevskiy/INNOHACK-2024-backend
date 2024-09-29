package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import origin.client.UserClient;
import origin.dto.project.AddProjectDto;
import origin.dto.project.GetProjectDto;
import origin.dto.user.ProfileUserDto;
import origin.model.project.Project;
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
@Tag(name = "ProjectController")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserClient userClient;

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

    @PutMapping("/{projectId}")
    public GetProjectDto updateProjectById(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername, @RequestBody GetProjectDto getProjectDto) {
        Project project = projectService.getById(projectId);

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsOwner(project, participantUserId);

        return projectMapper.toDto(projectService.update(projectId, getProjectDto));
    }

    @DeleteMapping("/{projectId}")
    public GetProjectDto deleteProjectById(@PathVariable long projectId, @RequestHeader(value = "X-Username") String participantUsername) {
        Project project = projectService.getById(projectId);
        GetProjectDto getProjectDto = projectMapper.toDto(project);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsOwner(project, participantUserId);
        projectService.deleteById(projectId);
        return getProjectDto;
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

    @PostMapping("/{projectId}/uploadImage")
    public GetProjectDto uploadImageProject(@RequestHeader(value = "X-Username") String principalUsername,
                                  @PathVariable Long projectId, @RequestParam(required = true) MultipartFile file) {
        projectService.uploadImage(projectId, file);
        return projectMapper.toDto(projectService.getById(projectId));
    }

}

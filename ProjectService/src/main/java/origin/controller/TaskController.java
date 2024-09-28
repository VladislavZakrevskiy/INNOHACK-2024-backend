package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import origin.client.UserClient;
import origin.dto.task.AddTaskDto;
import origin.dto.task.GetTaskDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.model.task.Task;
import origin.service.ProjectService;
import origin.service.SpaceService;
import origin.service.StatusService;
import origin.service.TaskService;
import origin.utils.mapper.TaskMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "TaskController")
public class TaskController {

    private final UserClient userClient;
    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final SpaceService spaceService;
    private final ProjectService projectService;
    private final StatusService statusService;

    @GetMapping("/task")
    public List<GetTaskDto> getUserTasks(@RequestHeader(value = "X-Username") String participantUsername,
                                         @RequestParam(required = false) Integer limit) {
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();

        List<Task> tasks = taskService.getByExecutorId(participantUserId);
        Collections.reverse(tasks);

        if (limit == null) {
            limit = 10;
        }

        List<Task> forReturnTasks = tasks.size() > limit ? tasks.subList(0, limit) : tasks;
        return forReturnTasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @PutMapping("/task/{taskId}")
    public GetTaskDto updateTaskById(@PathVariable long taskId, @RequestHeader(value = "X-Username") String participantUsername, @RequestBody GetTaskDto getTaskDto) {
        Task task = taskService.getById(taskId);
        Space space = spaceService.getById(task.getStatus().getSpace().getId());
        Project project = projectService.getById(task.getStatus().getSpace().getProject().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        taskService.validateUserIsMember(space, project, participantUserId);

        return taskMapper.toDto(taskService.update(taskId, getTaskDto));
    }

    @GetMapping("/{projectId}/space/{spaceId}/status/{statusId}/task")
    public List<GetTaskDto> getAllTasks(@PathVariable long projectId, @PathVariable long spaceId,
                                        @PathVariable long statusId,
                                        @RequestHeader(value = "X-Username") String participantUsername,
                                        @RequestParam(required = false) Integer limit){
        Project project = projectService.getById(projectId);
        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        projectService.validateUserIsMember(project, participantUserId);
        Space space = spaceService.getById(spaceId);
        spaceService.validateUserIsOwner(space, project, participantUserId);
        Status status = statusService.getById(statusId);
        List<Task> tasks = status.getTasks();
        Collections.reverse(tasks);
        if(limit==null){
            limit = 10;
        }
        List<Task> forReturnTasks = tasks.size() > limit ? tasks.subList(0, limit) : tasks;
        return forReturnTasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
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
        Status status = statusService.getById(statusId);
        Task task = new Task();
        task.setTitle(addTaskDto.getName());
        task.setDescription(addTaskDto.getDescription());
        task.setDeadlineDate(addTaskDto.getDeadlineDate());
        task.setCreateDate(LocalDateTime.now());
        task.setStatus(status);
        task.setOwnerId(participantUserId);
        task.setExecutorId(participantUserId);
        return taskMapper.toDto(taskService.save(task));
    }

    @DeleteMapping("/task/{taskId}")
    public GetTaskDto deleteTaskById(@PathVariable long taskId, @RequestHeader(value = "X-Username") String participantUsername) {
        Task task = taskService.getById(taskId);
        GetTaskDto getTaskDto = taskMapper.toDto(task);
        Space space = spaceService.getById(task.getStatus().getSpace().getId());

        Long participantUserId = userClient.getUserByUsername(participantUsername).getId();
        taskService.validateUserIsOwner(space, task, participantUserId);

        taskService.deleteById(taskId);

        return getTaskDto;
    }

    @PostMapping("/task/{taskId}/uploadImage")
    public GetTaskDto uploadImageTask(@RequestHeader(value = "X-Username") String principalUsername,
                                      @PathVariable Long taskId, @RequestParam(required = true) MultipartFile file) {
        taskService.uploadImage(taskId, file);
        return taskMapper.toDto(taskService.getById(taskId));
    }
}

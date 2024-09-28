package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.dto.task.GetTaskDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.model.task.Task;
import origin.repository.StatusRepository;
import origin.repository.TaskRepository;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getByExecutorId(long id) {
        return taskRepository.findAllByExecutorId(id);
    }

    public void validateUserIsMember(Space space, Project project, Long userId) {
        if (!space.getMembersId().contains(userId) && !project.getOwnerId().equals(userId)) {
            throw new ApiException("Вы не являетесь участником данного space", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateUserIsOwner(Space space, Task task, Long userId) {
        if (!space.getOwnerId().equals(userId) && !task.getOwnerId().equals(userId)) {
            throw new ApiException("Недостаточно прав", HttpStatus.BAD_REQUEST);
        }
    }

    public Task getById(long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найдена task с данным id", HttpStatus.NOT_FOUND)
        );
    }

    public Task update(long id, GetTaskDto getTaskDto) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найдена task с данным id", HttpStatus.NOT_FOUND)
        );

        if (getTaskDto.getTitle() != null) task.setTitle(getTaskDto.getTitle());
        if (getTaskDto.getDescription() != null) task.setDescription(getTaskDto.getDescription());
        if (getTaskDto.getOwner() != null && getTaskDto.getOwner().getId() != null) task.setOwnerId(getTaskDto.getOwner().getId());
        if (getTaskDto.getExecutor() != null && getTaskDto.getExecutor().getId() != null) task.setExecutorId(getTaskDto.getExecutor().getId());
        if (getTaskDto.getDeadlineDate() != null) task.setDeadlineDate(getTaskDto.getDeadlineDate());
        if (getTaskDto.getStatusId() != null) {
            Status status = statusRepository.findById(getTaskDto.getStatusId()).orElseThrow(
                    () -> new ApiException("Не найден status с данным id", HttpStatus.NOT_FOUND)
            );
            task.setStatus(status);
        }

        return taskRepository.save(task);
    }

    public void deleteById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найдена task с данным id", HttpStatus.NOT_FOUND)
        );
        taskRepository.deleteById(id);
    }
}

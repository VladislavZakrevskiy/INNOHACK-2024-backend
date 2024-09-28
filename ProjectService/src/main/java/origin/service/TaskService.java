package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.space.Space;
import origin.model.task.Task;
import origin.repository.TaskRepository;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getByExecutorId(long id) {
        return taskRepository.findAllByExecutorId(id);
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

    public Task deleteById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найдена task с данным id", HttpStatus.NOT_FOUND)
        );
        taskRepository.deleteById(id);
        return task;
    }
}

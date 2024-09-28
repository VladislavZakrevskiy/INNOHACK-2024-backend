package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import origin.model.task.Task;
import origin.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task save(Task task){
        return taskRepository.save(task);
    }
}

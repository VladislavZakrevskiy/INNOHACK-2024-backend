package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import origin.model.task.Task;
import origin.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getByExecutorId(long id) {
        return taskRepository.findByExecutorId(id);
    }
}

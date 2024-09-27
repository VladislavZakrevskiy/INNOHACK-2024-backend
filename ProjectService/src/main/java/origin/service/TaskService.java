package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import origin.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
}

package origin.service;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import origin.dto.task.GetTaskDto;
import origin.dto.task.UpdateTaskDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.model.task.Task;
import origin.repository.StatusRepository;
import origin.repository.TaskRepository;
import origin.service.firebase.FirebaseService;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;

    private final FirebaseService firebaseService;

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

    public Task update(long id, UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найдена task с данным id", HttpStatus.NOT_FOUND)
        );

        if (updateTaskDto.getTitle() != null) task.setTitle(updateTaskDto.getTitle());
        if (updateTaskDto.getDescription() != null) task.setDescription(updateTaskDto.getDescription());
        if (updateTaskDto.getOwnerId() != null) task.setOwnerId(updateTaskDto.getOwnerId());
        if (updateTaskDto.getExecutorId() != null) task.setExecutorId(updateTaskDto.getExecutorId());
        if (updateTaskDto.getDeadlineDate() != null) task.setDeadlineDate(updateTaskDto.getDeadlineDate());
        if(updateTaskDto.getLabels() != null) task.setLabels(updateTaskDto.getLabels());
        if(updateTaskDto.getCheckpoint() != null) task.setCheckpoint(updateTaskDto.getCheckpoint());
        if(updateTaskDto.getPlace() != null) task.setPlace(updateTaskDto.getPlace());
        if (updateTaskDto.getStatusId() != null) {
            Status status = statusRepository.findById(updateTaskDto.getStatusId()).orElseThrow(
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

    public void uploadImage(Long taskId, MultipartFile file)  {
        Task task = getById(taskId);
        Blob blob = firebaseService.uploadFile(file);
        String link = blob.getMediaLink();
        task.getImage().add(link);
        save(task);
    }
}

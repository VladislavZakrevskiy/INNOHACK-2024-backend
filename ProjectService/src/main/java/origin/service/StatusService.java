package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.dto.status.GetStatusDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
import origin.model.task.Task;
import origin.repository.SpaceRepository;
import origin.repository.StatusRepository;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;


    public Status save(Status status){
        return statusRepository.save(status);
    }

    public Status getById(Long id){
        return statusRepository.findById(id).orElseThrow(() ->
                new ApiException("Нет статуса с данным ID", HttpStatus.NOT_FOUND));
    }

    public void validateUserIsOwner(Space space, Project project, Long userId) {
        if (!space.getOwnerId().equals(userId) && !project.getOwnerId().equals(userId)) {
            throw new ApiException("Недостаточно прав", HttpStatus.BAD_REQUEST);
        }
    }

    public Status update(long id, GetStatusDto getStatusDto) {
        Status status = statusRepository.findById(id).orElseThrow(
                () -> new ApiException("Не найден status с данным id", HttpStatus.NOT_FOUND)
        );

        if (getStatusDto.getName() != null) status.setName(getStatusDto.getName());

        return statusRepository.save(status);
    }

    public void deleteById(long id) {
        Status status = getById(id);
        statusRepository.deleteById(id);
    }

}

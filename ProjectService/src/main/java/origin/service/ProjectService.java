package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.project.Project;
import origin.repository.ProjectRepository;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> findAllByMemberId(Long id){
        return projectRepository.findAllByMemberId(id);
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public Project getById(Long id){
        return projectRepository.findById(id).orElseThrow(() ->
                new ApiException("Не найден проект с данным id", HttpStatus.NOT_FOUND));
    }

    public void validateUserIsMember(Project project, Long userId) {
        if (!project.getMembersId().contains(userId)) {
            throw new ApiException("Пользователь " + userId+" не являетесь участником данного проекта", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateUserIsOwner(Project project, Long userId) {
        if (!project.getOwnerId().equals(userId)) {
            throw new ApiException("Недостаточно прав", HttpStatus.BAD_REQUEST);
        }
    }





}

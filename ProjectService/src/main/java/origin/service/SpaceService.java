package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.repository.ProjectRepository;
import origin.repository.SpaceRepository;
import origin.utils.exception.ApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;


    public List<Space> getAllProject(Project project){
        return spaceRepository.getAllByProject(project);
    }

    public Space save(Space space){
        return spaceRepository.save(space);
    }

    public Space getById(Long id){
        return spaceRepository.findById(id).orElseThrow(() ->
                new ApiException("Не найден space с данным id", HttpStatus.NOT_FOUND));
    }

    public void validateUserIsMember(Space space, Long userId) {
        if (!space.getMembersId().contains(userId)) {
            throw new ApiException("Вы не являетесь участником данного space", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateUserIsOwner(Space space, Long userId) {
        if (!space.getOwnerId().equals(userId)) {
            throw new ApiException("Недостаточно прав", HttpStatus.BAD_REQUEST);
        }
    }
}

package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.status.Status;
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



}

package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import origin.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

}

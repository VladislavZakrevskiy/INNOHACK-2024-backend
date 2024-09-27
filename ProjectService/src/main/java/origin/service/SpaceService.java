package origin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import origin.repository.SpaceRepository;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;
}

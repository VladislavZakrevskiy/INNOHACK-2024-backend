package origin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import origin.model.project.Project;
import origin.model.space.Space;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    public List<Space> getAllByProject(Project project);
}

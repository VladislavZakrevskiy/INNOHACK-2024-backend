package origin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import origin.model.space.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
}

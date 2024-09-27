package origin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import origin.model.status.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}

package origin.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import origin.model.project.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE :memberId IN elements(p.membersId)")
    List<Project> findAllByMemberId(@Param("memberId") Long memberId);
}

package hit.final_project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatus(String status);
    List<Job> findByJobType(String jobType);
    List<Job> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}

package hit.final_project;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cicd_jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String jobType;
}

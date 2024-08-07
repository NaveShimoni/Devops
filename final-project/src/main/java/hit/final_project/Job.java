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

    public Job(){}
    public Job(String name, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String type) {
//        this.id = // need to generate id;
        this.jobName = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobType = type;
    }
}

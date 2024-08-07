package hit.final_project;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CICDJobDTO {

    private String jobName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String jobType;
    
}

package hit.final_project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import java.time.LocalDateTime;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private JobRepository cicdJobRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            cicdJobRepository.save(new Job("Job 1", "Pending", LocalDateTime.now(), LocalDateTime.now(), "Build"));
            cicdJobRepository.save(new Job("Job 2", "Success", LocalDateTime.now(), LocalDateTime.now(), "Deploy"));
        };
    }

}

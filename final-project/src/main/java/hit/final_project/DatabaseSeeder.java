package hit.final_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            logger.info("Seeding database with initial data...");

            User user1 = new User("John Doe", "john.doe@example.com",
                    "123456789", "123 Main St", "password123");
            User user2 = new User("Jane Doe", "jane.doe@example.com",
                    "987654321", "456 Elm St", "password1234");
            User user3 = new User("Jake Smith", "jake.smith@example.com", "111222333", "789 Pine St",
                   "password12345");

            userRepository.save(user1);
            logger.info("Created User: {}", user1);

            userRepository.save(user2);
            logger.info("Created User: {}", user2);

            userRepository.save(user3);
            logger.info("Created User: {}", user3);

            logger.info("Database seeding completed.");
        };
    }
}

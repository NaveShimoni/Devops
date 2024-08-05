package hit.final_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface UserRepository extends JpaRepository<User,Long> {
}

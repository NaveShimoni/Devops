package hit.final_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() {
        User user = new User("John Doe", "john.doe@example.com", "123456789", "123 Main St", "password123");
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/api/users", user, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetUserById() {
        User user = new User("John Doe", "john.doe@example.com", "123456789", "123 Main St", "password123");
        ResponseEntity<User> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/users", user, User.class);

        Long userId = createResponse.getBody().getId();
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/users/" + userId, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }
    
}

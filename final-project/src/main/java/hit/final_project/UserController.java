package hit.final_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User entity
 * This controller handles HTTP requests and responses for user interaction
 * and operations
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all the users in the User table using the UserService
     * @return an HTTP Response including a JSON with an Array of JSONs,
     * representing all the users in our database.
     * Successful requests results in status code 200
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
         User saveUser = userService.saveUser(user);
        // NOT good practice. alternatively return status 201 CREATED
         return ResponseEntity.ok(user);
    }

    // user performs HTTP GET request in the form of /api/user/20
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userService.findUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // /api/users/20
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user){
        User updatedUser = userService.updateUser(id, user);
        // change the status to reflect the change (which status code is suitable?)
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        // returns an HTTP Response with empty body and status code 204.
        // adds no-content header to the response
       //  return ResponseEntity.noContent().build();

        // returns an empty body in the response but status code 200
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public User updateUserPassword(@PathVariable long id, @RequestBody UpdatePasswordRequest passwordRequest){
        return userService.updateUserPassword(id,
                passwordRequest.getOldPassword(),
                passwordRequest.getNewPassword());
    }


}

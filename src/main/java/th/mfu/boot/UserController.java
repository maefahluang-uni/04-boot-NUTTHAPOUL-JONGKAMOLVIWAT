package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static Map<String, User> users = new HashMap<String, User>();

    // POST /users
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String username = user.getUsername();

        if (users.containsKey(username)) {
            // username ซ้ำ คืน 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        users.put(username, user);

        // คืน 200 OK 
        return ResponseEntity.ok("User registered successfully");
    }

    // GET /users
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> list() {
        return ResponseEntity.ok(users.values());
    }

    // GET /users/{username}
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = users.get(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);
    }
}

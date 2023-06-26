package de.ls5.wt2.auth;

import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/*
 * RESTful-API for registering, authenticating the users and
 * checking if they are "admin"/ have "admin" role.
 * Using UserService
 */
@Transactional
@RestController
@RequestMapping("/rest/users")
public class UserREST {

    @Autowired
    private UserService userService;

    /*
     * Register a new user
     *
     * @param user A DBUser object to be registered
     * @return ResponseEntity with an error message in case the given username is already registered/exists in database
     *         if not then ResponseEntity with a success message.
     */
    @PostMapping(path = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody DBUser user) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    /*
     * Authenticate a user
     *
     * @param authorizationHeader The Authorization header with an encoded token implement the basic authentication,
     * which can be decoded to retrieve the claimed identity(username, password) of the subject
     * @return ResponseEntity with an OK status code in case of successful authentication
     *         else ResponseEntity with an UNAUTHORIZED status code and "Invalid Credentials!" message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authorizationHeader) {

        String encodedToken = authorizationHeader.replace("Basic ", "");
        String decodedToken = new String(Base64.getDecoder().decode(encodedToken));
        String[] splitToken = decodedToken.split(":");

        String username = splitToken[0];
        String password = splitToken[1];

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            currentUser.login(token);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials!");
        }
    }

    /*
     * Check if the authenticated subject has "admin" role
     *
     * @return ResponseEntity with UNAUTHORIZED status code in case the subject is not authenticated
     *         or ResponseEntity with NOT_FOUND status code in case the authenticated subject cannot be
     *            found in database
     *         or ResponseEntity with boolean value true if the authenticated subject
     *            has "admin" role, else value false.
     */
    @GetMapping("/role")
    public ResponseEntity<Boolean> isAdmin() {

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DBUser user = (DBUser) subject.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(subject.hasRole("admin"));
    }
}

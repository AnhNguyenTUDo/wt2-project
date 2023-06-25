package de.ls5.wt2.auth;

import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Transactional
@RestController
@RequestMapping("/rest/users")
public class UserREST {
    private UserService userService;

    public UserREST(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody DBUser user) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        userService.createUser(user);
//        Map<String, String> response = Collections.singletonMap("message", "User registered successfully");
        return ResponseEntity.ok("User registered successfully");
    }
//////////////DO NOT DELETE, IS THE ORIGINAL
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authorizationHeader) {
        String encodedCredentials = authorizationHeader.replace("Basic ", "");
        String credentials = new String(Base64.getDecoder().decode(encodedCredentials));
        String[] usernamePassword = credentials.split(":");

        String username = usernamePassword[0];
        String password = usernamePassword[1];

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            currentUser.login(token);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials!");//.body("Login failed");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestHeader("Authorization") String authorizationHeader) {
//        String encodedCredentials = authorizationHeader.replace("Basic ", "");
//        String credentials = new String(Base64.getDecoder().decode(encodedCredentials));
//        String[] usernamePassword = credentials.split(":");
//
//        String username = usernamePassword[0];
//        String password = usernamePassword[1];
//
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//
//        try {
//            currentUser.login(token);
//            return ResponseEntity.ok(HttpStatus.OK);
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//        }
//    }

    @GetMapping("/role")
    public ResponseEntity<Boolean> isAdmin() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("//////////////////////////////////////////// IS AUTHENTICATED: " + subject.isAuthenticated());
        if (!subject.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DBUser user = (DBUser) subject.getPrincipal();
        if (user == null) {
            System.out.println("//////////////////////////////////////////// USER NULL");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean isAdmin = subject.hasRole("admin");
        return ResponseEntity.ok(isAdmin);
    }
}

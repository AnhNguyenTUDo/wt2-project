package de.ls5.wt2.auth;

import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/*
 * Initialize an account for admin if it doesn't exist in the database.
 */
@Component
public class AdminInitializer {

    @Autowired
    private UserService userService;


    /*
     * Initialize an admin account if it doesn't exist in the database.
     * (Will be automatically invoked by the Spring container after the bean is instantiated)
     */
    @PostConstruct
    public void initializeAdmin() {
        if(userService.getUserByUsername("admin") == null){

            DBUser admin = new DBUser();
            admin.setUsername("admin");
            admin.setPassword("admin");
            userService.createUser(admin);
        }
    }


}

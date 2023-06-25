package de.ls5.wt2.auth;

import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminInitializer {

    @Autowired
    private UserService userService;

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

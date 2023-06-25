package de.ls5.wt2.auth;

import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.MessageService;
import de.ls5.wt2.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        DBUser user = userService.getUserByUsername(username);

        if (user == null) {
//            throw new UnknownAccountException("User not found");
            return null;
        }

        // Create the authentication info with the user's password and realm name
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());

        // Optionally, you can store additional user information in the authentication info
        // For example, if you want to access the DBUser object later in the application
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(user, getName()));

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = (String) getAvailablePrincipal(principals);
//        DBUser user = userService.getUserByUsername(username);
        DBUser user = (DBUser) getAvailablePrincipal(principals);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // Assign the "user" role to all authenticated users
        authorizationInfo.addRole("user");

        // Assign the "admin" role to the user if they are an admin
        if (user.getUsername().equals("admin")) {
            authorizationInfo.addRole("admin");
        }

        return authorizationInfo;
    }

}

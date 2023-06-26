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

/*
* Custom realm for authentication and authorization using Apache Shiro
*/
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /*
     * Make the authentication process by using the provided UserService to retrieve the user
     * from the database based on the provided token. If such user exists, an  authentication info
     * with the user name, password and the realm name will be returned
     *
     * @param token A token that contains the user's credentials.
     * @return authentication info with username, password and realm name in case of successful authentication.
     * @throw AuthenticationException in case of an authentication error.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        DBUser user = userService.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());

        authenticationInfo.setPrincipals(new SimplePrincipalCollection(user, getName()));

        return authenticationInfo;
    }
    /*
     * Retrieves the user from the PrincipalCollection. Two roles are defined here: "user" and "admin"
     * All users are asigned with the "user" role including admin.
     * The user with the name "admin" will additionally be assigned with the "admin" role.
     *
     * @param principals The PrincipalCollection that contains the user's information.
     * @return Authorization info that contains the assigned roles.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        DBUser user = (DBUser) getAvailablePrincipal(principals);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //
        authorizationInfo.addRole("user");

        // Assign the "admin" role to the user if they are an admin
        if (user.getUsername().equals("admin")) {
            authorizationInfo.addRole("admin");
        }

        return authorizationInfo;
    }

}

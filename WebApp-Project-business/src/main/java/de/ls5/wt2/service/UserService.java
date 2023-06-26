package de.ls5.wt2.service;

import de.ls5.wt2.entity.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/*
 * A Service for managing the users(DBUser) in database, including
 * - retrieve a user by the username
 * - create a user
 * - retrieve messages from a user
 */
@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    /*
     * Retrieve a user by the username form database.
     *
     * @param username Name of user
     * @return
     */
    public DBUser getUserByUsername(String username) {

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);

        final Root<DBUser> from = query.from(DBUser.class);

        query.select(from).where(builder.equal(from.get("username"), username));

        TypedQuery<DBUser> typedQuery = this.entityManager.createQuery(query);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /*
     * Create a new user in database.
     *
     * @param dbUser a DBUser object representing the user to be created.
     */
    @Transactional
    public void createUser(DBUser dbUser) {

        final DBUser user = new DBUser();
        user.setUsername(dbUser.getUsername());
        user.setPassword(dbUser.getPassword());

        entityManager.persist(user);
    }

//    public List<DBMessage> getUserMessages(String username) {
//        DBUser user = getUserByUsername(username);
//        if (user != null) {
//            return user.getMessages();
//        }
//        return Collections.emptyList();
//    }
}


package de.ls5.wt2.service;

import de.ls5.wt2.entity.DBMessage;
import de.ls5.wt2.entity.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    public DBUser getUserByUsername(String username) {
        TypedQuery<DBUser> query = entityManager.createQuery("SELECT u FROM DBUser u WHERE u.username = :username", DBUser.class);
        query.setParameter("username", username);
        List<DBUser> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void createUser(DBUser user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());

        entityManager.persist(user);
    }

    public void deleteUser(String username) {
        DBUser user = entityManager.find(DBUser.class, username);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public List<DBMessage> getUserMessages(String username) {
        DBUser user = getUserByUsername(username);
        if (user != null) {
            return user.getMessages();
        }
        return Collections.emptyList();
    }
}


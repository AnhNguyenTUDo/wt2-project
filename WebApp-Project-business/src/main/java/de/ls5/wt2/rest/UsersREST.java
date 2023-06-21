package de.ls5.wt2.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBNews;
import de.ls5.wt2.entity.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping(path = "rest/user")
public class UsersREST {

    @Autowired
    private EntityManager entityManager;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public DBUser create(@RequestBody final DBUser param){

        final DBUser user = new DBUser();

        user.setName(param.getName());
        user.setPassword(param.getPassword());
        user.setMessages(param.getMessages());

        this.entityManager.persist(user);

        return user;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBUser> readAllUsers() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);

        final Root<DBUser> from = query.from(DBUser.class);

        query.select(from);

        return this.entityManager.createQuery(query).getResultList();
    }

    @GetMapping(path = "{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public DBUser readUser(@PathVariable("id") final long id){
        return this.entityManager.find(DBUser.class, id);
    }
}

package de.ls5.wt2.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBMessage;
import de.ls5.wt2.entity.DBMessage_;
import de.ls5.wt2.entity.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(path = "rest/messages")
public class MessagesREST {

    @Autowired
    private EntityManager entityManager;

    @PostMapping(path = "user/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBMessage create(@RequestBody final DBMessage param,
                            @PathVariable("id") final long id) {

        final DBMessage message = new DBMessage();

        message.setMessage(param.getMessage());
        message.setUser(this.entityManager.find(DBUser.class,id));
        this.entityManager.find(DBUser.class,id).getMessages().add(message);

        this.entityManager.persist(message);

        return message;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBMessage> readAllMessages() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);

        final Root<DBMessage> from = query.from(DBMessage.class);

        query.select(from);

        return this.entityManager.createQuery(query).getResultList();
    }

    @GetMapping(path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBMessage readMessage(@PathVariable("id") final long id) {
        return this.entityManager.find(DBMessage.class, id);
    }

}

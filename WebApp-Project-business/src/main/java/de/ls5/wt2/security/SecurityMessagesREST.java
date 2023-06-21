package de.ls5.wt2.security;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBMessage;
import de.ls5.wt2.entity.DBMessage_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping(path = "rest/security/messages")
public class SecurityMessagesREST {

    @Autowired
    private EntityManager entityManager;

//    @GetMapping(path = "newest",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public DBMessage readNewestNews() {
//        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);
//
//        final Root<DBMessage> from = query.from(DBMessage.class);
//
//        final Order order = builder.desc(from.get(DBMessage_.publishedOn));
//
//        query.select(from).orderBy(order);
//
//        return this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBMessage> readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);

        final Root<DBMessage> from = query.from(DBMessage.class);

        query.select(from);

        return this.entityManager.createQuery(query).getResultList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBMessage createUnsafe(@RequestBody final DBMessage param) {

        final DBMessage messages = new DBMessage();

//        messages.setHeadline(param.getHeadline());
         messages.setMessage(param.getMessage());
//        messages.setPublishedOn(new Date());

        this.entityManager.persist(messages);
        this.entityManager.flush();

        int upd = this.entityManager.createNativeQuery(
                        "UPDATE DBMessage SET content = '" + param.getMessage() + "' WHERE id = " + messages.getId() + ';')
                .executeUpdate();

        System.err.println(upd);

        return this.entityManager.find(DBMessage.class, messages.getId());
    }
}


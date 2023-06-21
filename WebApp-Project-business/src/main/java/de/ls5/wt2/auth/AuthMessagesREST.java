package de.ls5.wt2.auth;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import de.ls5.wt2.conf.auth.permission.ViewFirstFiveNewsItemsPermission;
import de.ls5.wt2.entity.DBMessage;
import de.ls5.wt2.entity.DBMessage_;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping(path = {"rest/auth/session/messages", "rest/auth/basic/messages", "rest/auth/jwt/messages"})
public class AuthMessagesREST {

    @Autowired
    private EntityManager entityManager;

//    @GetMapping(path = "newest",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<DBMessage> readNewestNews() {
//        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
//        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);
//
//        final Root<DBMessage> from = query.from(DBMessage.class);
//
//        final Order order = builder.desc(from.get(DBMessage_.publishedOn));
//
//        query.select(from).orderBy(order);
//
//        final List<DBMessage> result = this.entityManager.createQuery(query).getResultList();
//
//        // Attribute based permission check using permissions
//        final Subject subject = SecurityUtils.getSubject();
//        final Permission firstFiveNewsItemsPermission = new ViewFirstFiveNewsItemsPermission(result);
//
//        if (!subject.isPermitted(firstFiveNewsItemsPermission)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        return ResponseEntity.ok(result.get(0));
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DBMessage>> readAllAsJSON() {
        final Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);

        final Root<DBMessage> from = query.from(DBMessage.class);

        query.select(from);

        final List<DBMessage> result = this.entityManager.createQuery(query).getResultList();
        return ResponseEntity.ok(result);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBMessage create(@RequestBody final DBMessage param) {

        SecurityUtils.getSubject().checkRole("admin");

        final DBMessage messages = new DBMessage();

//        messages.setHeadline(param.getHeadline());
        messages.setMessage(param.getMessage());
//        messages.setPublishedOn(new Date());

        this.entityManager.persist(messages);

        return messages;
    }
}

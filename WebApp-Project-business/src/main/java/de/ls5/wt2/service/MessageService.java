package de.ls5.wt2.service;

import de.ls5.wt2.entity.DBMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private EntityManager entityManager;

    public MessageService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<DBMessage> getAllMessages() {
        TypedQuery<DBMessage> query = entityManager.createQuery(
                "SELECT m FROM DBMessage m", DBMessage.class);
        return query.getResultList();
    }

    public List<DBMessage> getMessagesByUsername(String username) {
        TypedQuery<DBMessage> query = entityManager.createQuery(
                "SELECT m FROM DBMessage m WHERE m.sender = :username", DBMessage.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Transactional
    public DBMessage createMessage(DBMessage message) {
        DBMessage msg = new DBMessage();
        msg.setContent(message.getContent());
        msg.setSender(message.getSender());
        entityManager.persist(message);
        entityManager.flush();
        return message;
    }

    public DBMessage getMessageById(Long id) {
        return entityManager.find(DBMessage.class, id);
    }

    @Transactional
    public DBMessage updateMessage(DBMessage message) {
        return entityManager.merge(message);
    }

    @Transactional
    public void deleteMessage(Long id) {
        DBMessage message = entityManager.find(DBMessage.class, id);
        if (message != null) {
            entityManager.remove(message);
            entityManager.flush();
        }
    }
}


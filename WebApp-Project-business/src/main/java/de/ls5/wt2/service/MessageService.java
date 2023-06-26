package de.ls5.wt2.service;

import de.ls5.wt2.entity.DBMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/*
 * A Service for managing the messages(DBMessage) in database, including
 * - retrieve all messages
 * - create a message
 * - retrieve a message by its id
 * - update/edit a message
 * - delete a message by its id
 */
@Service
public class MessageService {

    @Autowired
    private EntityManager entityManager;

    /*
     * Retrieve all messages from database.
     *
     * @return list of DBMessage objects representing all messages.
     */
    public List<DBMessage> getAllMessages() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);

        final Root<DBMessage> from = query.from(DBMessage.class);

        query.select(from);

        return this.entityManager.createQuery(query).getResultList();
    }

    /*
     *  Retrieve messages from a user by the username from database.
     *
     * @param username
     * @return list of DBMessage objects representing all messages.
     */
    public List<DBMessage> getMessagesByUsername(String username) {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();;
        final CriteriaQuery<DBMessage> query = builder.createQuery(DBMessage.class);

        final Root<DBMessage> from = query.from(DBMessage.class);

        query.select(from).where(builder.equal(from.get("sender"),username));

        TypedQuery<DBMessage> typedQuery = this.entityManager.createQuery(query);

        return typedQuery.getResultList();
    }

    /*
     * Create/Save a new message in database.
     *
     * @param message DBMessage object representing the message to be created
     * @return DBMessage object representing the message to be created
     */
    @Transactional
    public DBMessage createMessage(DBMessage message) {
        DBMessage msg = new DBMessage();
        msg.setContent(message.getContent());
        msg.setSender(message.getSender());
        this.entityManager.persist(message);
        this.entityManager.flush();
        return message;
    }

    /*
     * Retrieve a message by its id from database.
     *
     * @param id Id of the DBMessage object.
     * @return the message with this id from database.
     */
    public DBMessage getMessageById(Long id) {
        return entityManager.find(DBMessage.class, id);
    }

    /*
     * Update a message from database.
     *
     * @param message DBMessage object to be updated.
     * @return the updated DBMessage object
     */
    @Transactional
    public DBMessage updateMessage(DBMessage message) {
        return this.entityManager.merge(message);
    }

    /*
     * Delete a message by its id from database
     *
     * @param id Id of the DBMessage object to be deleted
     */
    @Transactional
    public void deleteMessage(Long id) {
        DBMessage message = this.entityManager.find(DBMessage.class, id);
        if (message != null) {
            this.entityManager.remove(message);
            this.entityManager.flush();
        }
    }
}


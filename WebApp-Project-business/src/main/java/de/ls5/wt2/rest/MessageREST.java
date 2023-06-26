package de.ls5.wt2.rest;

import de.ls5.wt2.entity.DBMessage;
import de.ls5.wt2.entity.DBUser;
import de.ls5.wt2.service.MessageService;
import de.ls5.wt2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * RESTful-API for:
 * - sending messages
 * - retrieving all messages
 * - retrieving a message by id
 * - updating/editing a message by id
 * - deleting a message by id
 * - authorizing a message by id (check if this message belongs to the subject)
 * Using MessageService and UserService
 */
@RestController
@RequestMapping("/rest/messages")
public class MessageREST {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    /*
     * Send a message.
     * Use UserService to set the subject as the sender for this message.
     * Use MessageService to save the message in database.
     *
     * @param message a DBMessage object
     * @return ResponseEntity with the to be sent message
     */
    @PostMapping
    public ResponseEntity<DBMessage> sendMessage(@RequestBody DBMessage message) {
        Subject subject = SecurityUtils.getSubject();

        DBUser user = (DBUser) subject.getPrincipal();

        String username = user.getUsername();

        DBUser dbUser = userService.getUserByUsername(username);
        message.setSender(dbUser);

        DBMessage sentMessage = messageService.createMessage(message);

        return ResponseEntity.ok(sentMessage);
    }

    /*
     * Retrieve all messages.
     * Use MessageService to retrieve all messages from database.
     *
     * @return ResponseEntity with the messages list.
     */
    @GetMapping
    public ResponseEntity<List<DBMessage>> getAllMessages() {

        List<DBMessage> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /*
     * Retrieve a message by its id.
     * Use MessageService to retrieve a message by its id from database.
     *
     * @param id Id of the message
     * @return ResponseEntity with a not found status if the message is not exist with the given id
     *         or ResponseEntity with the message with this id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DBMessage> getMessageById(@PathVariable Long id) {
        DBMessage message = messageService.getMessageById(id);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        if (!message.getSender().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(message);
    }

    /*
     * Update/Edit the message by its id.
     * Use MessageService to update/edit a message by its id from database.
     *
     * @param id Id of the message to be updated/edited
     * @param updatedMessage message to be updated
     * @return ResponseEntity with a not found status if the message is not exist with the given id
     *         or ResponseEntity with FORBIDDEN status if the subject is not the sender of this
     *            message and is not admin
     *         or ResponseEntity with the updated Message if the subject is the sender of this
     *            message or is an admin.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DBMessage> updateMessage(@PathVariable Long id, @RequestBody DBMessage messageToUpdate) {

        DBMessage message = messageService.getMessageById(id);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        Subject subject = SecurityUtils.getSubject();

        DBUser user = (DBUser) subject.getPrincipal();

        String username = user.getUsername();

        if(!subject.hasRole("admin")){
            if (!message.getSender().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        message.setContent(messageToUpdate.getContent());
        DBMessage updatedMessage = messageService.updateMessage(message);
        return ResponseEntity.ok(updatedMessage);
    }

    /*
     * Delete a message by its id.
     * Use MessageService to delete a message by its id from database.
     *
     * @param id Id of message to be deleted.
     * @return ResponseEntity with not found status if no message with this id exist
     *         or ResponseEntity with FORBIDDEN status if the subject is not the sender of this
     *            message and is not admin
     *         or ResponseEntity with no content if the subject is the sender of this
     *            message or is an admin.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        DBMessage message = messageService.getMessageById(id);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        Subject subject = SecurityUtils.getSubject();
        DBUser user = (DBUser) subject.getPrincipal();
        String username = user.getUsername();

        if(!subject.hasRole("admin")){
            if (!message.getSender().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * Check authorization of a message by its id.
     *
     * @param id Id of the message to be authorized
     * @return ResponseEntity with boolean false if the subject is not the sender
     *         or ResponseEntity with boolean true if the subject is the sender.
     */
    @GetMapping(path = "/authorization/{id}")
    public ResponseEntity<Boolean> checkAuthorization(@PathVariable long id){
        DBMessage message = messageService.getMessageById(id);

        Subject subject = SecurityUtils.getSubject();
        DBUser user = (DBUser) subject.getPrincipal();
        String username = user.getUsername();

        if (!message.getSender().getUsername().equals(username)){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}

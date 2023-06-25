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

@RestController
@RequestMapping("/rest/messages")
public class MessageREST {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<DBMessage> sendMessage(@RequestBody DBMessage message) {
        Subject subject = SecurityUtils.getSubject();
//        String username = (String) subject.getPrincipal();
        DBUser user = (DBUser) subject.getPrincipal();

        String username = user.getUsername();
        // Set the sender of the message to the currently authenticated user
        DBUser dbUser = userService.getUserByUsername(username);
        message.setSender(dbUser);

        DBMessage savedMessage = messageService.createMessage(message);

//        MessageResponse response = new MessageResponse(savedMessage.getId(), savedMessage.getContent(), savedMessage.getSender());
//        System.out.println("///////////////////////////////////////  ID "+ response.getId());
//        System.out.println("///////////////////////////////////////  CONTENT " + response.getContent());
//        System.out.println("/////////////////////////////////////// SENDER " + response.getSender());

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<DBMessage>> getAllMessages() {
//        Subject subject = SecurityUtils.getSubject();
////        String username = (String) subject.getPrincipal();
//
//        DBUser user = (DBUser) subject.getPrincipal();
//
//        String username = user.getUsername();

        List<DBMessage> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<DBMessage> updateMessage(@PathVariable Long id, @RequestBody DBMessage updatedMessage) {
        DBMessage message = messageService.getMessageById(id);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        Subject subject = SecurityUtils.getSubject();
//        String username = (String) subject.getPrincipal();

        DBUser user = (DBUser) subject.getPrincipal();

        String username = user.getUsername();

        if(!subject.hasRole("admin")){
            if (!message.getSender().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        message.setContent(updatedMessage.getContent());
        DBMessage savedMessage = messageService.updateMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

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

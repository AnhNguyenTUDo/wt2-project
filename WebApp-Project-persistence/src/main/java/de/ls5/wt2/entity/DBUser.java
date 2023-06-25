package de.ls5.wt2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class DBUser extends DBIdentified {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<DBMessage> messages = new ArrayList<>();

    public DBUser() {
    }

    public DBUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER, orphanRemoval = true )//, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("sender")
    public List<DBMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<DBMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(DBMessage message) {
        messages.add(message);
        message.setSender(this);
    }
}

package de.ls5.wt2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Data model for message entity in database,
 * which contains attributes (id, content, sender)
 * and has many to one relationship with user entity.
 */
@Entity
@Table(name = "messages")
public class DBMessage extends DBIdentified{

    @Column(nullable = false)
    private String content;

    private DBUser sender;


    public DBMessage() {
    }

    public DBMessage(String content, DBUser sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("messages")
    public DBUser getSender() {
        return sender;
    }

    public void setSender(DBUser sender) {
        this.sender = sender;
    }
}

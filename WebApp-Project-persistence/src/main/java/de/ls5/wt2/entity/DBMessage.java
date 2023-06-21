package de.ls5.wt2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DBMessage extends DBIdentified {

    private String message;
    private DBUser user;

    public void setMessage(String message) {this.message = message;}

    public String getMessage() {return message;}

    public void setUser(DBUser user) {this.user = user;}

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("messages")
    public DBUser getUser(){return user;}

}
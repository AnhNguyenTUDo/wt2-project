package de.ls5.wt2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class DBUser extends DBIdentified {

    private String name;
    private String password;
    private Set<DBMessage> messages;

    public DBUser() {
        this.messages = new HashSet<>();
    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    public Set<DBMessage> getMessages() {return messages;}

    public void setMessages(Set<DBMessage> messages) { this.messages = messages;}

}
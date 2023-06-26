package de.ls5.wt2.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*
 * Data model representing no real entity in database,
 * is used to generate unique values  (primary keys) in subclasses
 */
@MappedSuperclass
public class DBIdentified {

    private long id;

    @Id
    @GeneratedValue
    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

}

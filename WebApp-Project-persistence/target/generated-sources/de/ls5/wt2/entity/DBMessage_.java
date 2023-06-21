package de.ls5.wt2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBMessage.class)
public abstract class DBMessage_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBMessage, String> message;
	public static volatile SingularAttribute<DBMessage, DBUser> user;

	public static final String MESSAGE = "message";
	public static final String USER = "user";

}


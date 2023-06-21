package de.ls5.wt2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBUser.class)
public abstract class DBUser_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBUser, String> password;
	public static volatile SingularAttribute<DBUser, String> name;
	public static volatile SetAttribute<DBUser, DBMessage> messages;

	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String MESSAGES = "messages";

}


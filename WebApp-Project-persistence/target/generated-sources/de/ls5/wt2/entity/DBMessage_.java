package de.ls5.wt2.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBMessage.class)
public abstract class DBMessage_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBMessage, DBUser> sender;
	public static volatile SingularAttribute<DBMessage, String> content;

	public static final String SENDER = "sender";
	public static final String CONTENT = "content";

}


package org.lgg.lucassite.dao.configuration;

import org.hibernate.SessionFactory;
import org.lgg.lucassite.dao.AbstractDAO;
import org.lgg.lucassite.model.configuration.ConfigurationAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDAO extends AbstractDAO<ConfigurationAttribute>
{
	@Autowired
	public ConfigurationDAO(SessionFactory sessionFactory)
	{
		super(ConfigurationAttribute.FIELD_ID, ConfigurationAttribute.class);
		setSessionFactory(sessionFactory);
	}
}

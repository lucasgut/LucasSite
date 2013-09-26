package org.lgg.lucassite.model.user;

import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This class represents a user session.
 */
@Component
@Scope("session")
public class UserSession
{
	final static Logger logger = LoggerFactory.getLogger(UserSession.class);
	
	@Autowired
	@Qualifier("configurationManager")
	private ConfigurationManager configurationManager;
	
	private String id = null;
	
    public UserSession()  {
    	logger.info("Creating new user session [" + this.toString() + "].");
    	id = String.valueOf(this.hashCode());
    	//configurationManager.increaseHitCounter();
    }
    
    public void setConfigurationManager(ConfigurationManager configurationManager) {
    	this.configurationManager = configurationManager;
    }
    
    public String getId() {
    	return id;
    }
}
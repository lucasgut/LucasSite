package org.lgg.lucassite.model.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lgg.lucassite.dao.configuration.ConfigurationDAO;
import org.lgg.lucassite.exception.DataException;
import org.lgg.lucassite.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="configurationManager")
public class ConfigurationManagerDB implements ConfigurationManager
{
	final static Logger logger = LoggerFactory.getLogger(ConfigurationManagerDB.class);
	
    @Autowired
    private ConfigurationDAO configurationDAO;

    private Map<String, User> loggedInUsers = new HashMap<String, User>();	//session id, user 
    
    /**
     * {@inheritDoc}
     */
    public List<ConfigurationAttribute> getConfigurationAttributes() {
    	logger.debug("Querying configuration attributes");
        return configurationDAO.findAll();
    }

    @Override
    public ConfigurationAttribute getConfigurationAttribute(String id) {
    	logger.debug("Querying configuration attribute [" + id + "].");
        return configurationDAO.findById(id); 
    }
    
	@Override
	public void loginUser(String userSessionId, User user) {
		logger.info("Logging-in user [" + user.getUserName() + "] on session [" + userSessionId + "].");
		loggedInUsers.put(userSessionId, user);
	}

	@Override
	public void logoutUser(String userSessionId) {
		logger.info("Logging-out user [" + this.getUser(userSessionId).getUserName() + "] on session [" + userSessionId + "].");
		loggedInUsers.remove(userSessionId);
	}
	
	@Override
	public boolean isUserLoggedIn(String userSessionId) {
		return userSessionId != null && loggedInUsers.containsKey(userSessionId);
	}
	
	@Override
	public User getUser(String userSessionId) {
		User user = null;
		if(userSessionId != null) {
			user = loggedInUsers.get(userSessionId);
		}
		return user;
	}
	
	@Override
	public int getHitCount() {
		return Integer.valueOf(getHitCountAttribute().getValue()).intValue();
	}
	
	private ConfigurationAttribute getHitCountAttribute() {
		ConfigurationAttribute hitCount;
		try {
			hitCount = configurationDAO.findById(ConfigurationManager.HIT_COUNTER);
			if(hitCount == null) {
				hitCount = new ConfigurationAttribute(ConfigurationManager.HIT_COUNTER, "0");
			}
		}
		catch(DataException de) {
			hitCount = new ConfigurationAttribute(ConfigurationManager.HIT_COUNTER, "0");
		}
		return hitCount;
	}

	public void increaseHitCounter() {
		ConfigurationAttribute hitCounter = getHitCountAttribute();
		int newHitCount = Integer.valueOf(hitCounter.getValue()).intValue() + 1;
		logger.info("Hit count: " + newHitCount);
		hitCounter.setValue(String.valueOf(newHitCount));
		configurationDAO.save(hitCounter);
	}

}
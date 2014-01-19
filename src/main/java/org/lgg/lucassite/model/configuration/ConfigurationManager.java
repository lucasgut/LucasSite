package org.lgg.lucassite.model.configuration;

import java.util.List;

import org.lgg.lucassite.model.user.User;

/**
 * Configuration manager
 */
public interface ConfigurationManager
{
	public static final String HIT_COUNTER = "HIT_COUNTER";
	
    /**
     * Retrieves all configuration attributes
     * 
     * @return list of downloads
     */
    public List<ConfigurationAttribute> getConfigurationAttributes();

    /**
     * Retrieves a configuration attribute
     * 
     * @return config attribute
     */
    public ConfigurationAttribute getConfigurationAttribute(String id);
    
    /**
     * Log-in a user
     * @param userSessionId session Id
     * @param user user to log-in
     */
    public boolean loginUser(String userSessionId, User user);

    /**
     * Log-out a user
     * @param userSessionId session Id
     */
    public void logoutUser(String userSessionId);
    
    /**
     * Check if a user is logged in
     * @param userSessionId user session id
     * @return true or false
     */
    public boolean isUserLoggedIn(String userSessionId);
    
    /**
     * Returns the specified user
     * @param userSessionId user session id
     * @return user
     */
    public User getUser(String userSessionId);
    
    /**
     * Return the hit count
     * @return hit count
     */
    public int getHitCount();
    
    /**
     * Increase the hit counter
     */
    public void increaseHitCounter();
}

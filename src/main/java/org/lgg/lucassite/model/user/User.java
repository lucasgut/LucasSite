package org.lgg.lucassite.model.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a User
 */
public class User
{
	final static Logger logger = LoggerFactory.getLogger(User.class);
	
	private String userName;
	private String password;
	
	public User() {
		userName = "";
		password = "";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof User)) return false;
		User user = (User) other;		
		return user == this || user.userName.equals(userName) && user.password.equals(password);
	}
	
    public User(String userName, String password)  {
    	this.userName = userName;
    	this.password = password;
    }
    
    public String getUserName() {
    	return userName;
    }
    
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
}
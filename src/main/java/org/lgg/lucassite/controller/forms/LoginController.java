package org.lgg.lucassite.controller.forms;

import javax.validation.Valid;

import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.lgg.lucassite.model.user.User;
import org.lgg.lucassite.model.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class LoginController 
{
    @Autowired private ConfigurationManager configurationManager;
    @Autowired private UserSession userSession;				//Session scoped

    public void setConfigurationManager(ConfigurationManager configurationManager)
    {
        this.configurationManager = configurationManager;
    }

    public void setUserSession(UserSession userSession)
    {
        this.userSession = userSession;
    }
	
    @RequestMapping(value="/Login.html",method=RequestMethod.GET)
    public ModelAndView showLoginPage() {
        final ModelAndView mav = new ModelAndView("Login");
        mav.addObject("hitcount", configurationManager.getHitCount());
        mav.addObject("isGuest", true);
        mav.getModelMap().put("user", new User());
        return mav;    	
    }
    
	@RequestMapping(value="/Login.html", method = RequestMethod.POST)
	public String processLoginForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "Login";
		}
		configurationManager.loginUser(userSession.getId(), user);
		return "Login";
	}

	@RequestMapping(value="/Logout.html", method = RequestMethod.GET)
	public String logout() {
		configurationManager.logoutUser(userSession.getId());
		return "Home";
	}	
}
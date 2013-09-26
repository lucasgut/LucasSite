package org.lgg.lucassite.controller.home;

import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.lgg.lucassite.model.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home page controller.
 * This controller wires in a session scope bean so it has
 * to be request scoped. 
 */
@Controller
@Scope("session")
public class HomeControllerImpl implements HomeController
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
    
    /**
     * {@inheritDoc}
     */
    public ModelAndView homePage()
    {
        final ModelAndView mav = new ModelAndView("Home");
        mav.addObject("hitcount", configurationManager.getHitCount());
        mav.addObject("isGuest", configurationManager.isUserLoggedIn(userSession.getId()) ? false : true);
        mav.addObject("user", configurationManager.getUser(userSession.getId()));
        return mav;
    }
}

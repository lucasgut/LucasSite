package org.lgg.lucassite.controller.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public interface HomeController
{
    /**
     * Renders the Home page.
     * 
     * @return the home page model
     */
    @RequestMapping(value = "/Home.html")
    ModelAndView homePage();
}

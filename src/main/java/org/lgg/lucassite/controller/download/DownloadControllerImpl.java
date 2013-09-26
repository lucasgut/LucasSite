package org.lgg.lucassite.controller.download;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.lgg.lucassite.model.download.Download;
import org.lgg.lucassite.model.download.Downloads;
import org.lgg.lucassite.model.download.DownloadsManager;
import org.lgg.lucassite.model.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class DownloadControllerImpl implements DownloadController
{
    @Autowired private DownloadsManager downloadsManager;
    @Autowired private ConfigurationManager configurationManager;
    @Autowired private UserSession userSession;				//Session scoped

    public void setDownloadManager(DownloadsManager downloadsManager)
    {
        this.downloadsManager = downloadsManager;
    }
    
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
    public ModelAndView downloadPage()
    {
        final ModelAndView mav = new ModelAndView("Downloads");
        mav.addObject("downloads", downloadsManager.getDownloads());
        mav.addObject("hitcount", configurationManager.getHitCount());
        mav.addObject("isGuest", configurationManager.isUserLoggedIn(userSession.getId()) ? false : true);
        mav.addObject("user", configurationManager.getUser(userSession.getId()));
        return mav;
    }
    
    /**
     * {@inheritDoc}
     */
    public ModelAndView downloadContentPage(@RequestParam Long downloadId)
    {
        final ModelAndView mav = new ModelAndView("DownloadContent");
        mav.addObject("download", downloadsManager.getDownload(downloadId));
        return mav;
    }    

    /**
     * {@inheritDoc}
     */
    @ResponseBody
    public Downloads downloads() {
        return new Downloads(downloadsManager.getDownloads());
    }

    /**
     * {@inheritDoc}
     */
    public void downloadImage(HttpServletResponse response, @RequestParam Long downloadId) throws IOException
    {
    	Download download = downloadsManager.getDownload(downloadId);
    	if(download == null) {
    		throw new IOException("Failed to load download [" + downloadId + "]");
    	}
    	byte[] image = download.getImage();
    	if(image != null) {
	        InputStream isImage = new ByteArrayInputStream(download.getImage());
	        response.setContentType("image/jpeg");
	        IOUtils.copy(isImage, response.getOutputStream());
    	}
    }    
}

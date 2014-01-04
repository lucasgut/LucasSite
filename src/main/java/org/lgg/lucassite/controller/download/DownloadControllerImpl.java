package org.lgg.lucassite.controller.download;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lgg.lucassite.DownloadsHelper;
import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.lgg.lucassite.model.download.Download;
import org.lgg.lucassite.model.download.Downloads;
import org.lgg.lucassite.model.download.DownloadsManager;
import org.lgg.lucassite.model.user.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class DownloadControllerImpl implements DownloadController
{
	final static Logger logger = LoggerFactory.getLogger(DownloadControllerImpl.class);
	
    @Autowired private DownloadsManager downloadsManager;
    @Autowired private ConfigurationManager configurationManager;
    @Autowired private UserSession userSession;				//Session scoped

    private String downloadsPath;
    
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
    
    @Value("#{lucasSiteProps.downloadsPath}")
    public void setDownloadsPath(String downloadsPath) {
    	this.downloadsPath = downloadsPath;
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
        mav.addObject("isGuest", configurationManager.isUserLoggedIn(userSession.getId()) ? false : true);
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
    @ResponseBody
    public List<String> downloadTitles() {
        return DownloadsHelper.sortedDownloadTitles(downloadsManager.getDownloads());
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

    /**
     * {@inheritDoc}
     */
    public void downloadFile(HttpServletResponse response, @RequestParam Long downloadId) throws IOException
    {
    	Download download = downloadsManager.getDownload(downloadId);
    	if(download == null) {
    		throw new IOException("Failed to load download [" + downloadId + "]");
    	}
    	String fileName = download.getUrl();
        Path path = FileSystems.getDefault().getPath(downloadsPath, fileName);
		logger.debug("Downloading file [" + path.getFileName() + "].");
        String mimeType = Files.probeContentType(path);
        if(mimeType == null) {
        	mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + path.getFileName());
        Files.copy(path, response.getOutputStream());
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteDownload(@PathVariable("downloadId") Long downloadId) {
		downloadsManager.deleteDownload(downloadId);
	}    
}

package org.lgg.lucassite.controller.download;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.lgg.lucassite.model.download.Downloads;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface DownloadController
{
    /**
     * Renders the Downloads page.
     * 
     * @return the downloads page model
     */
    @RequestMapping(value = "/Downloads.html")
    ModelAndView downloadPage();

    /**
     * Renders the download content
     * 
     * @return the downloads page model
     */
    @RequestMapping(value = "/DownloadContent.html")
    ModelAndView downloadContentPage(@RequestParam Long downloadId);

    /**
     * Renders the image of a download
     * 
     * @return the image
     */
    @RequestMapping(value = "/DownloadImage.html")
    public void downloadImage(HttpServletResponse response, @RequestParam Long downloadId) throws IOException;
    
    /**
     * Returns a list of downloads
     * 
     * @return downloads.
     */
    @RequestMapping(value = "/Downloads.xml")
    Downloads downloads();
}
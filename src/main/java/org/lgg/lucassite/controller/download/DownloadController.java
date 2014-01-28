package org.lgg.lucassite.controller.download;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.lgg.lucassite.model.download.Downloads;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
     * Returns the file of a download
     * 
     * @return the file
     */
    @RequestMapping(value = "/DownloadFile.html")
    public void downloadFile(HttpServletResponse response, @RequestParam Long downloadId) throws IOException;
    
    /**
     * Returns a list of downloads
     * 
     * @return downloads.
     */
    @RequestMapping(value = "/Downloads.xml")
    Downloads downloads();

    /**
     * Returns a sorted list of download titles
     * 
     * @return download titles.
     */
    @RequestMapping(value = "/DownloadTitles", method=RequestMethod.GET)
    @ResponseBody List<String> downloadTitles();

    /**
     * Returns a sorted list of download titles as an atom feed
     * 
     * @return download titles.
     */
    @RequestMapping(value = "/DownloadTitlesFeed", method=RequestMethod.GET)
    ModelAndView downloadTitlesFeed();
    
    /**
     * Delete a download
     * @param downloadId download Id
     */
    @RequestMapping(value = "/Downloads({downloadId}).xml", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDownload(@PathVariable("downloadId") Long downloadId);
}

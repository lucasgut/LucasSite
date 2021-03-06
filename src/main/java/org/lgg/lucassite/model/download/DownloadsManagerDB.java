package org.lgg.lucassite.model.download;

import java.util.List;

import org.lgg.lucassite.dao.download.DownloadDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadsManagerDB implements DownloadsManager
{
	final static Logger logger = LoggerFactory.getLogger(DownloadsManagerDB.class);
	
    @Autowired
    private DownloadDAO downloadsDAO;

    /**
     * {@inheritDoc}
     */
    public List<Download> getDownloads() {
    	logger.debug("Querying downloads");
        return downloadsDAO.findAll();
    }
    
    /**
     * {@inheritDoc}
     */
    public void createDownload(Download download) {
    	logger.debug("Saving download [" + download.toString() + "].");
    	downloadsDAO.save(download);
    }

    /**
     * {@inheritDoc}
     */
    public void updateDownload(Download download) {
    	logger.debug("Updating download [" + download.toString() + "].");
    	downloadsDAO.merge(download);
    }
    
	@Override
	public Download getDownload(Long downloadId) {
    	logger.debug("Querying download [" + downloadId + "]");
        return downloadsDAO.findById(downloadId);
	}

	@Override
	public void deleteDownload(Long downloadId) {
    	logger.debug("Deleting download [" + downloadId + "]");
    	Download download = downloadsDAO.findById(downloadId);
        downloadsDAO.delete(download);
	}
}

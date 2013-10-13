package org.lgg.lucassite.model.download;

import java.util.List;

/**
 * Downloads manager
 */
public interface DownloadsManager
{
    /**
     * Retrieves all downloads
     * 
     * @return list of downloads
     */
    public List<Download> getDownloads();
    
    /**
     * Create a download
     * @param download download
     */
    public void createDownload(Download download);

    /**
     * Update a download
     * @param download download
     */
    public void updateDownload(Download download);
    
    /**
     * Return a download
     * @param downloadId download Id
     * @return download
     */
    public Download getDownload(Long downloadId);
}

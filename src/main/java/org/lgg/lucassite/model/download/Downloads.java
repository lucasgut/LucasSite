package org.lgg.lucassite.model.download;

import java.util.ArrayList;
import java.util.List;

/**
 * List of downloads
 */
public class Downloads
{
    private List<Download> downloads = new ArrayList<Download>();

    public Downloads() {
    }
    
    public Downloads(List<Download> downloads) {
        this.downloads = downloads;
    }
    
    public void setDownload(List<Download> downloads) {
        this.downloads = downloads;
    }

    public List<Download> getDownloads() {
        return downloads;
    }
}

package org.lgg.lucassite.controller.download;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.lgg.lucassite.model.download.Download;
import org.lgg.lucassite.model.download.DownloadsManager;

public class TestDownloadController {

	@Test
	public void testDownloadTitles() {
		List<Download> testList = new ArrayList<Download>();
		testList.add(new Download(new Long(1), "ABC", new Date(), "", "", null));
		testList.add(new Download(new Long(2), "DEF", new Date(), "", "", null));
		testList.add(new Download(new Long(3), "GHI", new Date(), "", "", null));
		DownloadsManager dm = mock(DownloadsManager.class);
		when(dm.getDownloads()).thenReturn(testList);
		
		DownloadControllerImpl c = new DownloadControllerImpl();
		c.setDownloadManager(dm);
		Assert.assertEquals(3, c.downloadTitles().size());
		Assert.assertEquals("ABC", c.downloadTitles().get(0));
		Assert.assertEquals("DEF", c.downloadTitles().get(1));
		Assert.assertEquals("GHI", c.downloadTitles().get(2));
	}

}

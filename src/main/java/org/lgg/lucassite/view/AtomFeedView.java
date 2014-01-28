package org.lgg.lucassite.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

public class AtomFeedView extends AbstractAtomFeedView {
    private final String feedId;
    private final String feedTitle;

    public AtomFeedView(String feedId, String feedTitle) {
    	this.feedId = feedId;
    	this.feedTitle = feedTitle;
    }
    
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        feed.setId(feedId);
        feed.setTitle(feedTitle);
    }

	@Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return null;
    }
}

package org.lgg.lucassite.dao.download;

import org.hibernate.SessionFactory;
import org.lgg.lucassite.model.download.Download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.lgg.lucassite.dao.AbstractDAO;

@Repository
public class DownloadDAO extends AbstractDAO<Download>
{
	@Autowired
	public DownloadDAO(SessionFactory sessionFactory)
	{
		super(Download.FIELD_ID, Download.class);
		setSessionFactory(sessionFactory);
	}
}

package org.lgg.lucassite.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.lgg.lucassite.exception.DataException;
import org.lgg.lucassite.model.download.DownloadsManagerDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Abstract class providing basic DAO implementation methods.  
 *
 * @param <T>
 */
public class AbstractDAO<T> extends HibernateDaoSupport implements DAO<T> {
	final static Logger logger = LoggerFactory.getLogger(DownloadsManagerDB.class);

    protected Class<T> entityClass;
    protected String tableName;
    protected String orderFieldName;

    /**
     * Constructor which uses the entityClass simple name as the database table name.
     * 
     * @param orderFieldName
     * @param entityClass
     */
    public AbstractDAO(final String orderFieldName, final Class<T> entityClass)
    {
        this(entityClass.getSimpleName(), orderFieldName, entityClass);
    }

    /**
     * Complete constructor where the name of the table might be different from the name of the bean which represents it.
     * 
     * @param tableName
     *            in database
     * @param orderFieldName
     *            E.g. "Id" or "firstName"
     * @param entityClass
     *            that represents the database table
     */
    public AbstractDAO(final String tableName, final String orderFieldName, final Class<T> entityClass)
    {
        this.tableName = tableName;
        this.orderFieldName = orderFieldName;
        this.entityClass = entityClass;
    }

    /** {@inheritDoc} */
    public void save(final T transientInstance)
    {
        logger.debug("About to save object of type " + entityClass);
        try
        {
            getHibernateTemplate().save(transientInstance);
            logger.debug("Saved");
        }
        catch (final RuntimeException re)
        {
        	logger.error("Failed to save object of type", entityClass, re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public void delete(final T persistentInstance)
    {
    	logger.debug("About to delete instance of", entityClass);
        try
        {
            getHibernateTemplate().delete(persistentInstance);
            logger.debug("Delete successful");
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to delete instance of", entityClass, re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public T findById(final String id)
    {
        logger.debug("About to find instance of", entityClass + " with id: " + id);
        try
        {
            final T instance = (T) getHibernateTemplate().get(this.entityClass, id);
            return instance;
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to find instance of " + entityClass + " with id: " + id, re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public T findById(final Long id)
    {
        logger.debug("About to find instance of", entityClass + " with id: " + id.toString());
        try
        {
            final T instance = (T) getHibernateTemplate().get(this.entityClass, id);
            return instance;
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to find instance of " + entityClass + " with id: " + id, re);
            throw new DataException(re);
        }
    }
    
    @SuppressWarnings("unchecked")
    /** {@inheritDoc} */
    public List<T> findAll()
    {
        logger.debug("About to find all instances of " + entityClass);
        try
        {
            String queryString = "FROM " + tableName;
            queryString += " ORDER BY " + orderFieldName;
            return getHibernateTemplate().find(queryString);
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to find all instances of " + entityClass, re);
            throw new DataException(re);
        }
    }

    @SuppressWarnings("unchecked")
    /** {@inheritDoc} */
    public int getRowCount()
    {
        logger.debug("About to get a row count of table " + tableName);
        try
        {
            final Criteria criteria = this.getSession(false).createCriteria(this.entityClass);
            criteria.setProjection(Projections.rowCount());
            final List<Integer> results = criteria.list();
            return results.get(0);
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to get row count of table " + tableName, re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public T merge(final T detachedInstance)
    {
        logger.debug("Merging instance of type " + entityClass);
        try
        {
            final T result = (T) getHibernateTemplate().merge(detachedInstance);
            logger.debug("Merged succesfully");
            return result;
        }
        catch (final RuntimeException re)
        {
            logger.error("Merge of instance type " + entityClass + " failed", re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public void deleteAll()
    {
        logger.debug("About to delete all records from the " + tableName);
        try
        {
            // flush all changes to the database to avoid issues when deleting.
            this.getHibernateTemplate().flush();
            final int rows = this.getHibernateTemplate().bulkUpdate("delete from " + tableName);
            logger.debug("successfully " + rows + " deleted");
            this.getHibernateTemplate().clear();
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to delete all rows from " + tableName, re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public void flushAndClear()
    {
        try
        {
            flush();
            this.getHibernateTemplate().clear();
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to flush and clear the cache", re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public void flush()
    {
        try
        {
            this.getHibernateTemplate().flush();
        }
        catch (final RuntimeException re)
        {
            logger.error("Failed to flush the cache", re);
            throw new DataException(re);
        }
    }

    /** {@inheritDoc} */
    public T update(final T instance)
    {
        return merge(instance);
    }

    /**
     * Limits the number of results given the start position and the number of results required.
     * 
     * @param criteria
     *            criteria object to apply limit to
     * @param start
     *            the start position of the limit, E.g. row 15 = 15
     * @param fetchSize
     *            the amount to fetch, E.g. 20 rows.
     */
    protected void limitCriteria(final Criteria criteria, final int start, final int fetchSize)
    {
        criteria.setFirstResult(start);
        criteria.setFetchSize(fetchSize);
        criteria.setMaxResults(fetchSize);
    }
}

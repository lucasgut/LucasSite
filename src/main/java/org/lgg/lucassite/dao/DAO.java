package org.lgg.lucassite.dao;

import java.util.List;

public interface DAO<T>
{
	/**
	 * Save an instance to the table.
	 * 
	 * @param instance
	 *            row to add to table
	 */
	void save(T instance);

	/**
	 * Delete the supplied instance from the database table.
	 * 
	 * @param instance
	 *            to delete from the table
	 */
	void delete(T instance);

	/**
	 * Finds the specific instance in the database which matches the 'id' passed in.
	 * 
	 * @param id
	 *            Id to find in database
	 * @return the instance with the given id, or <code>null</code> if it is not found.
	 */
	T findById(String id);

	/**
	 * Finds the specific instance in the database which matches the 'id' passed in.
	 * 
	 * @param id
	 *            Id to find in database
	 * @return the instance with the given id, or <code>null</code> if it is not found.
	 */
	T findById(Long id);
	
	/**
	 * Find all instances in a table. The SQL equivalent is - select * from table.
	 * 
	 * @return List of table rows
	 */
	List<T> findAll();

	/**
	 * Returns the row count for the particular table.
	 * 
	 * @return the number of rows in the table
	 */
	int getRowCount();

	/**
	 * Merge changes.
	 */
	T merge(T detachedInstance);

	/**
	 * Delete all instances from the database. Equivalent to delete from table.
	 */
	void deleteAll();

	/**
	 * Flushes the changes in the cache to the database and clears the cache. If any stored
	 * procedures or native sql code is used, this must be called first.
	 */
	void flushAndClear();

	/**
	 * Flushes the changes in the cache to the database.
	 * ALWAYS run before a stored procedure!
	 */
	void flush();

	/**
	 * Update the instance.
	 */
	T update(T instance);
}

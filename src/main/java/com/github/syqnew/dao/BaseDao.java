package com.github.syqnew.dao;

import java.util.List;

/**
 * This interface is inherited by all other dao objects. 
 * 
 * @author snew
 *
 * @param <T> table
 */
public interface BaseDao<T> {

	/**
	 * Returns a list of all the objects/rows in the table
	 * @return List<T>
	 */
	List<T> findAll();

	/**
	 * Saves an object/row into the table
	 * @param BaseObject
	 */
	void persist(T BaseObject);

	/**
	 * Updates an existing object/row in the table
	 * @param BaseObject
	 */
	void merge(T BaseObject);

	/**
	 * Removes the given object/row in the table
	 * @param BaseObject
	 */
	void remove(T BaseObject);

	/**
	 * Returns the number of objects/rows in the table
	 * @return int
	 */
	int count();

}

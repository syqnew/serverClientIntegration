package com.github.syqnew.dao;

import com.github.syqnew.domain.News;

/**
 * News Data Access Object
 * @author snew
 *
 */
public interface NewsDao extends BaseDao<News> {
	
	/**
	 * Get all the News objects/rows that are associated with a client id
	 * @param id
	 * @return
	 */
	News getById(int id);

}

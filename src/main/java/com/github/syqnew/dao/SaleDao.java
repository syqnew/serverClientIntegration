package com.github.syqnew.dao;

import java.util.List;

import com.github.syqnew.domain.Sale;

/**
 * Sale Data Access Object
 * 
 * @author snew
 *
 */
public interface SaleDao extends BaseDao<Sale> {

	/**
	 * Find all the sales associated with a client
	 * 
	 * @param id
	 * @return List<Sale>
	 */
	List<Sale> findByClient(int id);
}

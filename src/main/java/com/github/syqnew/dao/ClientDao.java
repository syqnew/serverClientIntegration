package com.github.syqnew.dao;

import com.github.syqnew.domain.Client;

/**
 * Client Data Access Object
 * 
 * @author snew
 *
 */
public interface ClientDao extends BaseDao<Client> {
	
	/**
	 * Find client with a given email
	 * 
	 * @param email; must be nonempty
	 * @return Client
	 */
	Client findByEmail(String email);
	
	/**
	 * Find client with a given id
	 * @param id
	 * @return Client
	 */
	Client findById(int id);

}

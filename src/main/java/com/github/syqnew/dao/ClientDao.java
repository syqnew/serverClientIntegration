package com.github.syqnew.dao;

import com.github.syqnew.domain.Client;

public interface ClientDao extends BaseDao<Client> {
	
	Client findByEmail(String email);

}

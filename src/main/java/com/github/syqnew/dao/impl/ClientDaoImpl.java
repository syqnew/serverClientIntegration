package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.domain.Client;

public class ClientDaoImpl extends BaseDaoImpl<Client> implements ClientDao {
	
	public ClientDaoImpl() {
		super(Client.class);
	}

}

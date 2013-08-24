package com.github.syqnew.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.domain.Client;
import com.github.syqnew.domain.MarketOrder;

public class MarketOrderDaoImpl extends BaseDaoImpl<MarketOrder> implements MarketOrderDao {
	
	public MarketOrderDaoImpl() {
		super(MarketOrder.class);
	}


}

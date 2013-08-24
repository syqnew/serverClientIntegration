package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.domain.MarketOrder;

public class MarketOrderDaoImpl extends BaseDaoImpl<MarketOrder> implements MarketOrderDao {
	
	public MarketOrderDaoImpl() {
		super(MarketOrder.class);
	}

	public List<MarketOrder> getByClient(int client) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Query query = session.createQuery("from " + MarketOrder.class.getName() + " where client = :client");
        query.setParameter("client", client);
        List<MarketOrder> orders = query.list();
		
		session.close();
		return orders;
	}


}

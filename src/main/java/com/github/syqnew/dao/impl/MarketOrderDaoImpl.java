package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.domain.MarketOrder;

public class MarketOrderDaoImpl extends BaseDaoImpl<MarketOrder> implements
		MarketOrderDao {

	public MarketOrderDaoImpl() {
		super(MarketOrder.class);
	}

	public List<MarketOrder> getByClient(int client) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("FROM " + MarketOrder.class.getName()
				+ " WHERE client = :client");
		query.setParameter("client", client);
		List<MarketOrder> orders = query.list();
		session.flush();
		session.close();
		return orders;
	}

	public List<MarketOrder> getLimitBuys() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("FROM " + MarketOrder.class.getName()
				+ " WHERE status = 0 AND orderType = 3 ORDER BY price DESC");
		List<MarketOrder> orders = query.list();
		session.flush();
		session.close();
		return orders;
	}

	public List<MarketOrder> getLimitSells() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("FROM " + MarketOrder.class.getName()
				+ " WHERE status = 0 AND orderType = 4 ORDER BY price ASC");
		List<MarketOrder> orders = query.list();
		session.flush();
		session.close();
		return orders;
	}

	public List<MarketOrder> getMarketBuys() {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();

			Query query = session
					.createQuery("FROM "
							+ MarketOrder.class.getName()
							+ " WHERE status = 0 AND orderType = 1 ORDER BY time ASC");
			List<MarketOrder> orders = query.list();
			session.flush();
			session.close();
			return orders;
	}

	public List<MarketOrder> getMarketSells() {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();

			Query query = session
					.createQuery("FROM "
							+ MarketOrder.class.getName()
							+ " WHERE status = 0 AND orderType = 2 ORDER BY time ASC");
			List<MarketOrder> orders = query.list();
			session.flush();
			session.close();
			return orders;
	}

}

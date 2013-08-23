package com.github.syqnew.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.MarketStateDao;
import com.github.syqnew.domain.MarketState;

public class MarketStateDaoImpl extends BaseDaoImpl<MarketState> implements MarketStateDao {
	
	public MarketStateDaoImpl() {
		super(MarketState.class);
	}

	public int getCurrentYear() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Criteria criteria = session.createCriteria(MarketState.class)
				.setProjection(Projections.max("year"));
		
		int currentYear = (Integer) criteria.uniqueResult();
		session.clear();
		session.close();
		
		return currentYear;
	}

}

package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.MarketStateDao;
import com.github.syqnew.domain.MarketState;

public class MarketStateDaoImpl extends BaseDaoImpl<MarketState> implements MarketStateDao {
	
	public MarketStateDaoImpl() {
		super(MarketState.class);
	}

	public MarketState getCurrentMarketState() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		DetachedCriteria maxYear = DetachedCriteria.forClass(MarketState.class)
				.setProjection(Projections.max("year"));
		
		List<MarketState> list = session.createCriteria(MarketState.class).add(Property.forName("year").eq(maxYear)).list();

		session.close();
		
		return list.get(0);
	}


}

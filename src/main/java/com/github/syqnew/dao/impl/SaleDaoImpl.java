package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.domain.MarketOrder;
import com.github.syqnew.domain.Quote;
import com.github.syqnew.domain.Sale;

public class SaleDaoImpl extends BaseDaoImpl<Sale> implements SaleDao {

	public SaleDaoImpl() {
		super(Sale.class);
	}

	public List<Sale> findByClient(int id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("FROM " + Sale.class.getName()
				+ " WHERE buyerId = :id OR sellerId = :id");
		List<Sale> sales = query.list();
		session.flush();
		session.close();
		return sales;
	}

}

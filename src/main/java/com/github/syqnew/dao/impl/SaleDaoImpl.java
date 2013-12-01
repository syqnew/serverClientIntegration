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

/**
 * Implementation of the interface SaleDao
 * 
 * @author snew
 *
 */
public class SaleDaoImpl extends BaseDaoImpl<Sale> implements SaleDao {

	/**
	 * Creates a SaleDaoImpl object
	 */
	public SaleDaoImpl() {
		super(Sale.class);
	}

	/**
	 * See spec in the interface SaleDao
	 */
	public List<Sale> findByClient(int id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("FROM " + Sale.class.getName()
				+ " WHERE buyerId = :id OR sellerId = :id");
		query.setParameter("id", id);
		List<Sale> sales = query.list();
		session.flush();
		session.close();
		return sales;
	}

}

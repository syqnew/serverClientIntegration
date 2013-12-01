package com.github.syqnew.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.QuoteDao;
import com.github.syqnew.domain.Quote;

/**
 * Implementation of the interface QuoteDao
 * 
 * @author snew
 *
 */
public class QuoteDaoImpl extends BaseDaoImpl<Quote> implements QuoteDao {

	/**
	 * Creates a QuoteDaoImpl object
	 */
	public QuoteDaoImpl() {
		super(Quote.class);
	}

	/**
	 * See spec in the interface QuoteDao
	 */
	public Quote getLastQuote() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("from " + Quote.class.getName()
				+ " order by time");
		query.setMaxResults(1);
		Quote quote = (Quote) query.list().get(0);
		session.flush();
		session.close();
		return quote;
	}

}

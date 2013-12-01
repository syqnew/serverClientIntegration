package com.github.syqnew.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.domain.Client;

/**
 * Implementation of the ClientDao interface
 * @author snew
 *
 */
public class ClientDaoImpl extends BaseDaoImpl<Client> implements ClientDao {
	
	/**
	 * Creates a ClientDao object
	 */
	public ClientDaoImpl() {
		super(Client.class);
	}

	/**
	 * See spec in the interface ClientDao
	 */
	public Client findByEmail(String email) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		Query query = session.createQuery("from " + Client.class.getName() + " where email = :email");
        query.setParameter("email", email);
        Client client = (Client) query.list().get(0);
        session.flush();
		session.close();
		return client;
	}

	/**
	 * See spec in the interface ClientDao
	 */
	public Client findById(int id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Client client = (Client) session.get(Client.class, id);
		session.flush();
		session.close();
		return client;
	}

}

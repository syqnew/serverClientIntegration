package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.BaseDao;

/**
 * Implementation of the BaseDao interface
 * @author snew
 *
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	private Class<T> type;

	/**
	 * Creates a BaseDaoImpl Object
	 * @param type
	 */
	public BaseDaoImpl(Class<T> type) {
		this.type = type;
	}

	/**
	 * See spec in the interface BaseDao
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		List<T> objects = session.createQuery("from " + type.getName()).list();
		session.close();
		return objects;
	}

	/**
	 * See spec in the interface BaseDao
	 */
	public void persist(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	/**
	 * See spec in the interface BaseDao
	 */
	public void merge(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.merge(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	/**
	 * See spec in the interface BaseDao
	 */
	public void remove(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.delete(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	/**
	 * See spec in the interface BaseDao
	 */
	public int count() {
		return findAll().size();
	}

}

package com.github.syqnew.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {
	private Class<T> type;

	public BaseDaoImpl(Class<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		List<T> objects = session.createQuery("from " + type.getName()).list();
		session.close();
		return objects;
	}

	public void persist(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	public void merge(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.merge(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	public void remove(T BaseObject) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.delete(type.getName(), BaseObject);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}

	public int count() {
		return findAll().size();
	}

}

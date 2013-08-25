package com.github.syqnew.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.syqnew.HibernateUtil;
import com.github.syqnew.dao.NewsDao;
import com.github.syqnew.domain.News;

public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao {
	
	public NewsDaoImpl() {
		super(News.class);
	}

	public News getById(int id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        News news = (News) session.get(News.class, id);
        session.close();
        return news;
	}

}

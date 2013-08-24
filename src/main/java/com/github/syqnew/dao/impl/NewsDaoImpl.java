package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.NewsDao;
import com.github.syqnew.domain.News;

public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao {
	
	public NewsDaoImpl() {
		super(News.class);
	}

}

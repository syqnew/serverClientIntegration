package com.github.syqnew.dao;

import java.util.List;

public interface BaseDao<T> {

	List<T> findAll();

	void persist(T BaseObject);

	void merge(T BaseObject);

	void remove(T BaseObject);

	int count();

}

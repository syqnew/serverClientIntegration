package com.github.syqnew.dao;

import java.util.List;

import com.github.syqnew.domain.Sale;

public interface SaleDao extends BaseDao<Sale> {

	List<Sale> findByClient(int id);
}

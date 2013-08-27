package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.domain.Sale;

public class SaleDaoImpl extends BaseDaoImpl<Sale> implements SaleDao {

	public SaleDaoImpl() {
		super(Sale.class);
	}

}

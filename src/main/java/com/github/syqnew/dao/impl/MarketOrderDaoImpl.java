package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.domain.MarketOrder;

public class MarketOrderDaoImpl extends BaseDaoImpl<MarketOrder> implements MarketOrderDao {
	
	public MarketOrderDaoImpl() {
		super(MarketOrder.class);
	}

}

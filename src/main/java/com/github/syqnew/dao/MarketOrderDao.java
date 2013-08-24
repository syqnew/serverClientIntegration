package com.github.syqnew.dao;

import java.util.List;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import com.github.syqnew.domain.MarketOrder;

public interface MarketOrderDao extends BaseDao<MarketOrder> {
	
	List<MarketOrder> getByClient(int client);
	
	List<MarketOrder> getLimitBuys();
	
	List<MarketOrder> getLimitSells();
	
	List<MarketOrder> getMarketOrders();
	
}

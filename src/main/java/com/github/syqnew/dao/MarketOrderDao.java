package com.github.syqnew.dao;

import java.util.List;

import com.github.syqnew.domain.MarketOrder;

public interface MarketOrderDao extends BaseDao<MarketOrder> {

	List<MarketOrder> getByClient(int client);

	List<MarketOrder> getLimitBuys();

	List<MarketOrder> getLimitSells();

	List<MarketOrder> getMarketBuys();

	List<MarketOrder> getMarketSells();

}

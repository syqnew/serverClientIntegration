package com.github.syqnew.dao;

import java.util.List;

import com.github.syqnew.domain.MarketOrder;

/**
 * MarketOrder Data Access Object
 * 
 * @author snew
 *
 */
public interface MarketOrderDao extends BaseDao<MarketOrder> {

	/**
	 * Get all orders associated with a client
	 * 
	 * @param client
	 * @return List<MarketOrder>
	 */
	List<MarketOrder> getByClient(int client);

	/**
	 * Get all limit buy orders
	 * @return List<MarketOrder>
	 */
	List<MarketOrder> getLimitBuys();

	/**
	 * Get all limit sell orders
	 * @return List<MarketOrder>
	 */
	List<MarketOrder> getLimitSells();

	/**
	 * Get all market buy orders
	 * @return List<MarketOrder>
	 */
	List<MarketOrder> getMarketBuys();

	/**
	 * Get all market buy orders
	 * @return List<MarketOrder>
	 */
	List<MarketOrder> getMarketSells();
	

}

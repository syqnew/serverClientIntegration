package com.github.syqnew.dao;

import com.github.syqnew.domain.MarketState;

/**
 * MarketState Data Access Object
 * @author snew
 *
 */
public interface MarketStateDao extends BaseDao<MarketState> {
	
	/**
	 * Get the current Market State object/row
	 * @return MarketState
	 */
	MarketState getCurrentMarketState();
	

}

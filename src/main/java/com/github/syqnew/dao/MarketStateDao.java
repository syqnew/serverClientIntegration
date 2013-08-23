package com.github.syqnew.dao;

import com.github.syqnew.domain.MarketState;

public interface MarketStateDao extends BaseDao<MarketState> {
	
	MarketState getCurrentMarketState();
	

}

package com.github.syqnew.dao;

import com.github.syqnew.domain.Quote;

public interface QuoteDao extends BaseDao<Quote> {
	
	Quote getLastQuote();

}

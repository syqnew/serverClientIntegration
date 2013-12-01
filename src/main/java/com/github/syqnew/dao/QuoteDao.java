package com.github.syqnew.dao;

import com.github.syqnew.domain.Quote;

/**
 * Quote Data Access Object
 * @author snew
 *
 */
public interface QuoteDao extends BaseDao<Quote> {
	
	/**
	 * Get the last quote
	 * @return Quote
	 */
	Quote getLastQuote();

}

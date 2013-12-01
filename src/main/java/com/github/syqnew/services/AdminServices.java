package com.github.syqnew.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.MarketStateDao;
import com.github.syqnew.dao.impl.MarketStateDaoImpl;
import com.github.syqnew.domain.MarketState;

/**
 * Operations:
 * - GET current market year/state
 * - POST market year/state 
 * 
 * @author snew
 *
 */
public class AdminServices {
	
	private MarketStateDao dao;

	/**
	 * Creates an AdminServices object
	 */
	public AdminServices() {
		dao = new MarketStateDaoImpl();
	}

	/**
	 * Get the current year/state of the market from the database
	 * 
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void getCurrentYear(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {
		MarketState currentMarketState = dao.getCurrentMarketState();
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(
				mapper.writeValueAsString(currentMarketState));
	}

	/**
	 * Insert a market state to the database
	 * 
	 * @param request
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void updateMarketState(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MarketState marketState = mapper.readValue(request.getReader(),
				MarketState.class);
		dao.persist(marketState);
	}

}

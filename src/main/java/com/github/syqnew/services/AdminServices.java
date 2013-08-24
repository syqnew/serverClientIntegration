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

public class AdminServices {

	public AdminServices() {
	};

	public void getCurrentYear(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {

		MarketStateDao dao = new MarketStateDaoImpl();
		MarketState currentMarketState = dao.getCurrentMarketState();
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(
				mapper.writeValueAsString(currentMarketState));
	}

	public void updateMarketState(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MarketState marketState = mapper.readValue(request.getReader(),
				MarketState.class);
		MarketStateDao dao = new MarketStateDaoImpl();
		dao.persist(marketState);
	}

}

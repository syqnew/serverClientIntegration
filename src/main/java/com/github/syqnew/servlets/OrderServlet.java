package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.MarketStateDao;
import com.github.syqnew.dao.impl.MarketStateDaoImpl;
import com.github.syqnew.domain.MarketState;

public class OrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		MarketStateDao dao = new MarketStateDaoImpl();
		MarketState currentMarketState = dao.getCurrentMarketState();

		ObjectMapper mapper = new ObjectMapper();

		response.getWriter().println(
				mapper.writeValueAsString(currentMarketState));
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		// adminServices.updateMarketState(request);
		ObjectMapper mapper = new ObjectMapper();
		MarketState marketState = mapper.readValue(request.getReader(),
				MarketState.class);

		MarketStateDao dao = new MarketStateDaoImpl();
		dao.persist(marketState);
		response.getWriter().println("POST");
	}

}
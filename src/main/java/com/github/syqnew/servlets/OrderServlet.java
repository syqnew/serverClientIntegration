package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.MarketStateDao;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.dao.impl.MarketStateDaoImpl;
import com.github.syqnew.domain.MarketOrder;
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

		ObjectMapper mapper = new ObjectMapper();
		MarketOrder order = mapper.readValue(request.getReader(),
				MarketOrder.class);

		MarketOrderDao dao = new MarketOrderDaoImpl();
		dao.merge(order);
		response.getWriter().println("POST");
	}

}

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
import com.github.syqnew.services.OrderServices;

public class OrderServlet extends HttpServlet {

	OrderServices orderServices = new OrderServices();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		orderServices.getMyOrders(request.getParameter("clientId"), request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		orderServices.submitOrder(request);
	}

}

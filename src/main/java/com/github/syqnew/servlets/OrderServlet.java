package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.OrderServices;

/**
 * Servlet that handles get and post requests to /order/*
 * 
 * @author snew
 *
 */
public class OrderServlet extends HttpServlet {

	private OrderServices orderServices = new OrderServices();

	/**
	 * Get request that is used by the students
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		orderServices.getMyOrders(request.getParameter("clientId"), request,
				response);
	}

	/**
	 * Post request that is used by the students
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		orderServices.submitOrder(request);
	}

}

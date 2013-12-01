package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.SaleServices;

/**
 * Servlet that handles get and post requests to /sale/*
 * 
 * @author snew
 *
 */
public class SaleServlet extends HttpServlet {
	
	private SaleServices saleServices = new SaleServices();
	
	/**
	 * Get request that is used by students
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		saleServices.getSales(request, response);
	}

	/**
	 * Get request that is used by students
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		saleServices.cancelOrder(request, response);
	}

}

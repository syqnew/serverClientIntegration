package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.ClientServices;

/**
 * Servlet that handles get and post requests to /client/*
 * 
 * @author snew
 *
 */
public class ClientServlet extends HttpServlet {

	private ClientServices clientServices = new ClientServices();

	/**
	 * Get request that is used by students
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		clientServices.getClient(request, response);
	}

	/**
	 * Post request that is used by students
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		clientServices.insertClient(request);
	}

}

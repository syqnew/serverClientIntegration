package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.AdminServices;


public class AdminServlet extends HttpServlet {
	
	private AdminServices adminServices = new AdminServices();
	
	// for students  
	// In mySQL: set global transaction isolation level read committed
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		adminServices.getCurrentYear(request, response);
	}

	// For admins
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		adminServices.updateMarketState(request);
	}

}

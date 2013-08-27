package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.ClientServices;

public class ClientServlet extends HttpServlet {

	ClientServices clientServices = new ClientServices();

	// curl localhost:8080/client/?email=pandas
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		clientServices.getClient(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		clientServices.insertClient(request);
	}

}

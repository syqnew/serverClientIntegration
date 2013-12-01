package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.MetadataServices;

/**
 * Servlet that handles get and post requests to /metadata/*
 * 
 * @author snew
 *
 */
public class MetadataServlet extends HttpServlet {
	
	private MetadataServices metadataServices = new MetadataServices();

	/**
	 * Get request that is used by students
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		metadataServices.getMetadataClient(request, response);
	}


}

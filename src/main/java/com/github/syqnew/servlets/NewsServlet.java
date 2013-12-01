package com.github.syqnew.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.services.NewsServices;

/**
 * Servlet that handles get and post requests to /news/*
 * 
 * @author snew
 *
 */
public class NewsServlet extends HttpServlet {
	
	private NewsServices newsServices = new NewsServices();

	/**
	 * Get request that is used by students
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		newsServices.getNews(request, response);
	}

	/**
	 * Post request that is used by the admin
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		newsServices.insertNews(request, response);
	}

}

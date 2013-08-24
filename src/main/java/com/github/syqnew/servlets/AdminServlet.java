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
import com.github.syqnew.services.*;


public class AdminServlet extends HttpServlet {
	
	private AdminServices adminServices;
	
	// for students  
	// In mySQL: set global transaction isolation level read committed
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		MarketStateDao dao = new MarketStateDaoImpl();
		MarketState currentMarketState = dao.getCurrentMarketState();
		
		ObjectMapper mapper = new ObjectMapper();	
		
		response.getWriter().println(mapper.writeValueAsString(currentMarketState));
	}

	// For admins
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		adminServices = new AdminServices();
		adminServices.updateMarketState(request);
	}

}

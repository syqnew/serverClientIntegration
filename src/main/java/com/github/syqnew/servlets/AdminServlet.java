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
		int currentYear = dao.getCurrentYear();
		
		response.getWriter().println(currentYear);
	}

	// For admins
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
//		adminServices.updateMarketState(request);
		ObjectMapper mapper = new ObjectMapper();
		MarketState marketState = mapper.readValue(request.getReader(), MarketState.class);

		MarketStateDao dao = new MarketStateDaoImpl();
		dao.persist(marketState);
		response.getWriter().println("POST");
	}

}

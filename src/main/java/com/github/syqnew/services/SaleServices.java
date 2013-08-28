package com.github.syqnew.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.dao.impl.SaleDaoImpl;
import com.github.syqnew.domain.Sale;

public class SaleServices {
	
	SaleDao dao;
	
	public SaleServices() {
		dao = new SaleDaoImpl();
	}
	
	public void getClientSales(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String clientId = request.getParameter("clientId");
		int id = Integer.parseInt(clientId);
		ObjectMapper mapper = new ObjectMapper();		
		List<Sale> sales = dao.findByClient(id);
		response.getWriter().println(mapper.writeValueAsString(sales));
	}

}

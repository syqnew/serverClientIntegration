package com.github.syqnew.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.dao.impl.SaleDaoImpl;
import com.github.syqnew.domain.MarketOrder;
import com.github.syqnew.domain.Sale;

public class SaleServices {

	SaleDao dao;
	MarketOrderDao orderDao;

	public SaleServices() {
		dao = new SaleDaoImpl();
		orderDao = new MarketOrderDaoImpl();
	}

	public void getSales(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> list = new ArrayList<String>();
		String clientId = request.getParameter("clientId");
		int id = Integer.parseInt(clientId);
		if (id == -1) {
			List<Sale> sales = dao.findAll();
			for (Sale sale : sales) {
				list.add(mapper.writeValueAsString(sale));
			}
			response.getWriter().println(list);
		} else {

			List<Sale> sales = dao.findByClient(id);
			for (Sale sale : sales) {
				list.add(mapper.writeValueAsString(sale));
			}
			response.getWriter().println(list);
		}
	}

	public void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MarketOrder order = mapper.readValue(request.getReader(), MarketOrder.class);
		order.cancelOrder();
		orderDao.merge(order);
	}

}

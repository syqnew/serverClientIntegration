package com.github.syqnew.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.domain.MarketOrder;

public class OrderServices {

	public OrderServices(){}
	
	public void submitOrder(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MarketOrder order = mapper.readValue(request.getReader(), MarketOrder.class);
		MarketOrderDao dao = new MarketOrderDaoImpl();
		dao.merge(order);
	}

}

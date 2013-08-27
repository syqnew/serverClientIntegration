package com.github.syqnew.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.dao.impl.ClientDaoImpl;
import com.github.syqnew.domain.Client;

public class ClientServices {

	private ClientDao dao; 
	
	public ClientServices() {
		dao = new ClientDaoImpl();
	}

	public void getClient(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Client client = dao.findByEmail(request.getParameter("email"));
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(mapper.writeValueAsString(client));
	}

	public void insertClient(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Client client = mapper.readValue(request.getReader(), Client.class);
		dao = new ClientDaoImpl();
		dao.persist(client);
	}

}

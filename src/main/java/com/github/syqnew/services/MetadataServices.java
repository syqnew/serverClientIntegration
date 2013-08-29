package com.github.syqnew.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.dao.MetadataDao;
import com.github.syqnew.dao.impl.ClientDaoImpl;
import com.github.syqnew.dao.impl.MetadataDaoImpl;
import com.github.syqnew.domain.Client;
import com.github.syqnew.domain.Metadata;

public class MetadataServices {
	
	MetadataDao dao; 
	ClientDao clientDao;
	
	public MetadataServices() {
		dao = new MetadataDaoImpl();
		clientDao = new ClientDaoImpl();
	}
	
	public void getMetadataClient(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Metadata metadata = dao.findAll().get(0);
		
		if ( request.getParameter("clientId").equals("-1") ) {
			response.getWriter().println(mapper.writeValueAsString(metadata));
			
		} else {
			List<String> list = new ArrayList<String>();
			
			String clientId = request.getParameter("clientId");
			int id = Integer.parseInt(clientId);
			Client client = clientDao.findById(id);
			
			list.add(mapper.writeValueAsString(metadata));
			list.add(mapper.writeValueAsString(client));
			
			response.getWriter().println(list);
		}
		
	}

}

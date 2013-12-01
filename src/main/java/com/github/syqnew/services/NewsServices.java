package com.github.syqnew.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.NewsDao;
import com.github.syqnew.dao.impl.NewsDaoImpl;
import com.github.syqnew.domain.News;

/**
 * Operations:
 * - GET news given an id
 * - POST news
 * @author snew
 *
 */
public class NewsServices {

	private NewsDao dao;

	/**
	 * Creates a NewsServices Object
	 */
	public NewsServices() {
		dao = new NewsDaoImpl();
	}

	/**
	 * Get the news associated with a certain id from the database
	 * 
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void getNews(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);
		News news = dao.getById(id);
		
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(mapper.writeValueAsString(news));
	}

	/**
	 * Insert News into the database
	 * 
	 * @param request
	 * @param response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void insertNews(HttpServletRequest request,
			HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		News[] news = mapper.readValue(request.getReader(), News[].class);
		for (News newz : news) {
			dao.persist(newz);
		}
	}
}

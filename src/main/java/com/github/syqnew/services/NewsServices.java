package com.github.syqnew.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.NewsDao;
import com.github.syqnew.dao.impl.NewsDaoImpl;
import com.github.syqnew.domain.News;

public class NewsServices {

	NewsDao dao;

	public NewsServices() {
		dao = new NewsDaoImpl();
	}

	public void getNews(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<String> list = new ArrayList<String>();

		ObjectMapper mapper = new ObjectMapper();
		List<News> news = dao.findAll();
		for (News newss : news) {
			list.add(mapper.writeValueAsString(newss));
		}
		response.getWriter().println(list);
	}

	public void insertNews(HttpServletRequest request,
			HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		News news = mapper.readValue(request.getReader(), News.class);
		dao.persist(news);
	}

}

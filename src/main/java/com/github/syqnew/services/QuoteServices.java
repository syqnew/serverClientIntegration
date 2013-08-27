package com.github.syqnew.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.syqnew.dao.QuoteDao;
import com.github.syqnew.dao.impl.QuoteDaoImpl;
import com.github.syqnew.domain.Quote;

public class QuoteServices {

	QuoteDao dao;

	public QuoteServices() {
		dao = new QuoteDaoImpl();
	}

	public void getAllQuotes(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {
		List<Quote> quote = dao.findAll();

		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(mapper.writeValueAsString(quote));
	}

	public void insertQuote(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		Quote quote = mapper.readValue(request.getReader(), Quote.class);
		dao.persist(quote);
	}

}

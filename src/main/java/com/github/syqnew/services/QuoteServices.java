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

/**
 * Operations:
 * - GET all quotes or just the last one
 * - POST a quote
 * 
 * @author snew
 *
 */
public class QuoteServices {

	private QuoteDao dao;

	/**
	 * Creates a QuoteServices object
	 */
	public QuoteServices() {
		dao = new QuoteDaoImpl();
	}

	/**
	 * Get all the quotes or get the most recent quote from the database
	 * 
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void getQuotes(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		if (request.getParameter("number").equals("last")) {
			Quote quote = dao.getLastQuote();
			response.getWriter().println(mapper.writeValueAsString(quote));
		} else {
			List<Quote> quote = dao.findAll();
			response.getWriter().println(mapper.writeValueAsString(quote));
		}
	}

	/**
	 * Insert a quote into the database
	 * 
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void insertQuote(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException,
			JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		Quote quote = mapper.readValue(request.getReader(), Quote.class);
		dao.persist(quote);
	}

}

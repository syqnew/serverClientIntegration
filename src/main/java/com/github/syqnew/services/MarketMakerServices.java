package com.github.syqnew.services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.QuoteDao;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.dao.impl.QuoteDaoImpl;
import com.github.syqnew.domain.MarketOrder;
import com.github.syqnew.domain.Quote;

public class MarketMakerServices {

	MarketOrderDao dao;
	QuoteDao quoteDao;

	public MarketMakerServices() {
		dao = new MarketOrderDaoImpl();
		quoteDao = new QuoteDaoImpl();
	}

	public void handleMarketOrders(HttpServletRequest request,
			HttpServletResponse response) {

		List<MarketOrder> marketOrders = dao.getMarketOrders();

		if (marketOrders.size() > 0) {
			// get earliest MarketOrder
			MarketOrder currentOrder = marketOrders.remove(0);
			int type = currentOrder.getOrderType();
			int size = currentOrder.getAmount();
			long currentTime = new Date().getTime();
			// Market buy
			if (type == 1) {
				List<MarketOrder> sellList = dao.getLimitSells();
				if (sellList.size() > 0) {
					MarketOrder bestAsk = sellList.remove(0);
					int volume = bestAsk.getAmount();
					int price = bestAsk.getPrice();

					if (size > volume) {
						bestAsk.fulfillOrder(volume);
						currentOrder.fulfillOrder(volume);
						dao.merge(bestAsk);
						dao.merge(currentOrder);
						
						Quote quote = new Quote(price, volume, currentTime);
						quote.setAsk(price);
						quote.setAskSize(volume);
						quote.setTime(currentTime);
						quoteDao.persist(quote);
					} else {
						bestAsk.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestAsk);
						dao.merge(currentOrder);
						
						Quote quote = new Quote(price, size, currentTime);
						quote.setAsk(price);
						quote.setAskSize(size);
						quote.setTime(currentTime);
						quoteDao.persist(quote);
					}

				}
			} else {
				List<MarketOrder> buyList = dao.getLimitBuys();
				if (buyList.size() > 0) {
					MarketOrder bestBid = buyList.remove(0);
					int volume = bestBid.getAmount();
					int price = bestBid.getPrice();

					if (size > volume) {
						bestBid.fulfillOrder(volume);
						currentOrder.fulfillOrder(volume);
						dao.merge(bestBid);
						dao.merge(currentOrder);
						
						Quote quote = new Quote(price, volume, currentTime);
						quote.setBid(price);
						quote.setBidSize(volume);
						quote.setTime(currentTime);
						quoteDao.persist(quote);
					} else {
						bestBid.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestBid);
						dao.merge(currentOrder);
						
						Quote quote = new Quote(price, size, currentTime);
						quote.setBid(price);
						quote.setBidSize(size);
						quote.setTime(currentTime);
						quoteDao.persist(quote);
					}
				}
			}

		}
	}

}

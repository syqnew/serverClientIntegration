package com.github.syqnew.services;

import java.util.Date;
import java.util.List;

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
	
	public void handleOrders() {
		handleMarketOrders();
		handleLimitOrders();
	}

	private synchronized void handleMarketOrders() {

		List<MarketOrder> marketOrders = dao.getMarketOrders();

		if (marketOrders.size() > 0) {
			// get earliest MarketOrder
			MarketOrder currentOrder = marketOrders.remove(0);
			int type = currentOrder.getOrderType();
			int size = currentOrder.getUnfulfilled();
			long currentTime = new Date().getTime();
			// Market buy
			if (type == 1) {
				List<MarketOrder> sellList = dao.getLimitSells();
				if (sellList.size() > 0) {
					MarketOrder bestAsk = sellList.remove(0);
					int volume = bestAsk.getUnfulfilled();
					int price = bestAsk.getPrice();
					
					if (size > volume) {
						bestAsk.fulfillOrder(volume);
						currentOrder.fulfillOrder(volume);
						dao.merge(bestAsk);
						dao.merge(currentOrder);

						Quote quote = new Quote(price, volume, currentTime);
						quote.setAsk(price);
						quote.setAskSize(volume);
						quoteDao.persist(quote);
					} else {
						bestAsk.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestAsk);
						dao.merge(currentOrder);

						Quote quote = new Quote(price, size, currentTime);
						quote.setAsk(price);
						quote.setAskSize(size);
						quoteDao.persist(quote);
					}

				}
			} else {
				List<MarketOrder> buyList = dao.getLimitBuys();
				if (buyList.size() > 0) {
					MarketOrder bestBid = buyList.remove(0);
					int volume = bestBid.getUnfulfilled();
					int price = bestBid.getPrice();

					if (size > volume) {
						bestBid.fulfillOrder(volume);
						currentOrder.fulfillOrder(volume);
						dao.merge(bestBid);
						dao.merge(currentOrder);

						Quote quote = new Quote(price, volume, currentTime);
						quote.setBid(price);
						quote.setBidSize(volume);
						quoteDao.persist(quote);
					} else {
						bestBid.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestBid);
						dao.merge(currentOrder);

						Quote quote = new Quote(price, size, currentTime);
						quote.setBid(price);
						quote.setBidSize(size);
						quoteDao.persist(quote);
					}
				}
			}

		}
	}

	private synchronized void handleLimitOrders() {

		List<MarketOrder> limitBuys = dao.getLimitBuys();
		List<MarketOrder> limitSells = dao.getLimitSells();

		if (limitBuys.size() > 0 && limitSells.size() > 0) {
			for (MarketOrder limitBuy : limitBuys) {
				if (limitSells.indexOf(limitBuy) != -1) {
					long time = new Date().getTime();
					MarketOrder limitSell = limitSells.get(limitSells
							.indexOf(limitBuy));
					int price = limitBuy.getPrice();
					int buyAmount = limitBuy.getAmount();
					int sellAmount = limitSell.getAmount();

					if (buyAmount > sellAmount) {
						limitBuy.fulfillOrder(sellAmount);
						limitSell.fulfillOrder(sellAmount);
						dao.merge(limitBuy);
						dao.merge(limitSell);

						Quote quote = new Quote(price, sellAmount, time);
						quote.setAsk(price);
						quote.setAskSize(buyAmount);
						quote.setBid(price);
						quote.setBidSize(sellAmount);
						quote.setTime(time);
						quoteDao.persist(quote);
					} else {
						limitBuy.fulfillOrder(buyAmount);
						limitSell.fulfillOrder(buyAmount);
						dao.merge(limitBuy);
						dao.merge(limitSell);

						Quote quote = new Quote(price, buyAmount, time);
						quote.setAsk(price);
						quote.setAskSize(buyAmount);
						quote.setBid(price);
						quote.setBidSize(buyAmount);
						quote.setTime(time);
						quoteDao.persist(quote);
					}

				}
			}
		}
	}

}

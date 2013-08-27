package com.github.syqnew.services;

import java.util.Date;
import java.util.List;

import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.QuoteDao;
import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.dao.impl.ClientDaoImpl;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.dao.impl.QuoteDaoImpl;
import com.github.syqnew.dao.impl.SaleDaoImpl;
import com.github.syqnew.domain.Client;
import com.github.syqnew.domain.MarketOrder;
import com.github.syqnew.domain.Quote;

public class MarketMakerServices {

	MarketOrderDao dao;
	QuoteDao quoteDao;
	ClientDao clientDao;
	SaleDao saleDao;

	public MarketMakerServices() {
		dao = new MarketOrderDaoImpl();
		quoteDao = new QuoteDaoImpl();
		clientDao = new ClientDaoImpl();
		saleDao = new SaleDaoImpl();
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
			int marketclientId = currentOrder.getClient();
			long currentTime = new Date().getTime();
			List<MarketOrder> sellList = dao.getLimitSells();
			List<MarketOrder> buyList = dao.getLimitBuys();
			// Market buy
			if (type == 1) {
				if (sellList.size() > 0) {
					MarketOrder bestAsk = sellList.remove(0);
					int ask = bestAsk.getPrice();
					int askSize = bestAsk.getUnfulfilled();
					int askClient = bestAsk.getClient();

					if (size > askSize) {
						bestAsk.fulfillOrder(askSize);
						currentOrder.fulfillOrder(askSize);
						dao.merge(bestAsk);
						dao.merge(currentOrder);

						Quote quote = new Quote(ask, askSize, currentTime);
						quote.setAsk(ask);
						quote.setAskSize(askSize);
						if (buyList.size() > 0) {
							MarketOrder bidOrder = buyList.remove(0);
							int bid = bidOrder.getPrice();
							int bidSize = bidOrder.getUnfulfilled();
							quote.setBid(bid);
							quote.setBidSize(bidSize);
						}
						quoteDao.persist(quote);
						
						Client marketClient = clientDao.findById(marketclientId);
						Client limitClient = clientDao.findById(askClient);
						marketClient.buyShares(askSize, ask * askSize);
						limitClient.sellShares(askSize, ask * askSize);
						clientDao.merge(marketClient);
						clientDao.merge(limitClient);
					} else {
						bestAsk.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestAsk);
						dao.merge(currentOrder);

						Quote quote = new Quote(ask, size, currentTime);
						quote.setAsk(ask);
						quote.setAskSize(size);
						if (buyList.size() > 0) {
							MarketOrder bidOrder = buyList.remove(0);
							int bid = bidOrder.getPrice();
							int bidSize = bidOrder.getUnfulfilled();
							quote.setBid(bid);
							quote.setBidSize(bidSize);
						}
						quoteDao.persist(quote);
						
						Client marketClient = clientDao.findById(marketclientId);
						Client limitClient = clientDao.findById(askClient);
						marketClient.buyShares(size, ask * size);
						limitClient.sellShares(size, ask * size);
						clientDao.merge(marketClient);
						clientDao.merge(limitClient);
					}
				}
			} else {
				if (buyList.size() > 0) {
					MarketOrder bestBid = buyList.remove(0);
					int bid = bestBid.getPrice();
					int bidSize = bestBid.getUnfulfilled();
					int bidClient = bestBid.getClient();

					if (size > bidSize) {
						bestBid.fulfillOrder(bidSize);
						currentOrder.fulfillOrder(bidSize);
						dao.merge(bestBid);
						dao.merge(currentOrder);

						Quote quote = new Quote(bid, bidSize, currentTime);
						quote.setBid(bid);
						quote.setBidSize(bidSize);
						if (sellList.size() > 0) {
							MarketOrder askOrder = sellList.remove(0);
							int ask = askOrder.getPrice();
							int askSize = askOrder.getUnfulfilled();
							quote.setAsk(ask);
							quote.setAskSize(askSize);
						}
						quoteDao.persist(quote);
						Client marketClient = clientDao.findById(marketclientId);
						Client limitClient = clientDao.findById(bidClient);
						marketClient.buyShares(bidSize, bid * bidSize);
						limitClient.sellShares(bidSize, bid * bidSize);
						clientDao.merge(marketClient);
						clientDao.merge(limitClient);
					} else {
						bestBid.fulfillOrder(size);
						currentOrder.fulfillOrder(size);
						dao.merge(bestBid);
						dao.merge(currentOrder);

						Quote quote = new Quote(bid, size, currentTime);
						quote.setBid(bid);
						quote.setBidSize(size);
						if (sellList.size() > 0) {
							MarketOrder askOrder = sellList.remove(0);
							int ask = askOrder.getPrice();
							int askSize = askOrder.getUnfulfilled();
							quote.setAsk(ask);
							quote.setAskSize(askSize);
						}
						quoteDao.persist(quote);
						Client marketClient = clientDao.findById(marketclientId);
						Client limitClient = clientDao.findById(bidClient);
						marketClient.buyShares(size, bid * size);
						limitClient.sellShares(size, bid * size);
						clientDao.merge(marketClient);
						clientDao.merge(limitClient);
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

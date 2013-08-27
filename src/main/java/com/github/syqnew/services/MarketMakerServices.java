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
import com.github.syqnew.domain.Sale;

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
		updateQuote();
		handleMarketOrders();
		handleLimitOrders();
	}

	private synchronized void updateQuote() {
		List<MarketOrder> sellList = dao.getLimitSells();
		List<MarketOrder> buyList = dao.getLimitBuys();
		if (sellList.size() == 0 && buyList.size() == 0) return;
		long currentTime = new Date().getTime();
		Quote quote = new Quote(currentTime);
		if (sellList.size() > 0) {
			MarketOrder sell = sellList.get(0);
			quote.setAsk(sell.getPrice());
			quote.setAskSize(sell.getUnfulfilled());
		}
		if (buyList.size() > 0) {
			MarketOrder buy = buyList.get(0);
			quote.setBid(buy.getPrice());
			quote.setBidSize(buy.getAmount());
		}
		quoteDao.persist(quote);
	}

	private synchronized void handleMarketOrders() {

		List<MarketOrder> marketOrders = dao.getMarketOrders();

		if (marketOrders.size() > 0) {
			// get earliest MarketOrder
			MarketOrder currentOrder = marketOrders.get(0);
			int type = currentOrder.getOrderType();
			int size = currentOrder.getUnfulfilled();
			int marketclientId = currentOrder.getClient();
			long currentTime = new Date().getTime();
			List<MarketOrder> sellList = dao.getLimitSells();
			List<MarketOrder> buyList = dao.getLimitBuys();
			// Market buy
			if (type == 1) {
				if (sellList.size() > 0) {
					MarketOrder bestAsk = sellList.get(0);
					int ask = bestAsk.getPrice();
					int askSize = bestAsk.getUnfulfilled();
					int askClient = bestAsk.getClient();
					int smallerAmount;
					if (size > askSize)
						smallerAmount = askSize;
					else
						smallerAmount = size;

					bestAsk.fulfillOrder(smallerAmount);
					currentOrder.fulfillOrder(smallerAmount);
					dao.merge(bestAsk);
					dao.merge(currentOrder);

					Quote quote = new Quote(currentTime);
					if (smallerAmount > 0) {
						quote.setAsk(ask);
					} else {
						quote.setAsk(-1);
					}
					quote.setAskSize(smallerAmount);
					if (buyList.size() > 0) {
						MarketOrder bidOrder = buyList.get(0);
						int bid = bidOrder.getPrice();
						int bidSize = bidOrder.getUnfulfilled();
						quote.setBid(bid);
						quote.setBidSize(bidSize);
					}
					quoteDao.persist(quote);

					Client marketClient = clientDao.findById(marketclientId);
					Client limitClient = clientDao.findById(askClient);
					marketClient.buyShares(smallerAmount, ask * smallerAmount);
					limitClient.sellShares(smallerAmount, ask * smallerAmount);
					clientDao.merge(marketClient);
					clientDao.merge(limitClient);

					if (smallerAmount > 0) {
						Sale sale = new Sale(marketclientId, askClient,
								smallerAmount, ask, currentTime,
								currentOrder.getId(), bestAsk.getId());
						saleDao.persist(sale);
					}
				}

			} else {
				if (buyList.size() > 0) {
					MarketOrder bestBid = buyList.get(0);
					int bid = bestBid.getPrice();
					int bidSize = bestBid.getUnfulfilled();
					int bidClient = bestBid.getClient();
					int smallerAmount;

					if (size > bidSize)
						smallerAmount = bidSize;
					else
						smallerAmount = size;

					bestBid.fulfillOrder(smallerAmount);
					currentOrder.fulfillOrder(smallerAmount);
					dao.merge(bestBid);
					dao.merge(currentOrder);

					Quote quote = new Quote(currentTime);
					if (smallerAmount > 0) {
						quote.setBid(bid);
					} else {
						quote.setBid(-1);
					}
					quote.setBidSize(smallerAmount);
					if (sellList.size() > 0) {
						MarketOrder askOrder = sellList.get(0);
						int ask = askOrder.getPrice();
						int askSize = askOrder.getUnfulfilled();
						quote.setAsk(ask);
						quote.setAskSize(askSize);
					}
					quoteDao.persist(quote);

					Client marketClient = clientDao.findById(marketclientId);
					Client limitClient = clientDao.findById(bidClient);
					marketClient.sellShares(smallerAmount, bid * smallerAmount);
					limitClient.buyShares(smallerAmount, bid * smallerAmount);
					clientDao.merge(marketClient);
					clientDao.merge(limitClient);

					if (smallerAmount > 0) {
						Sale sale = new Sale(marketclientId, bidClient,
								smallerAmount, bid, currentTime,
								bestBid.getId(), currentOrder.getId());
						saleDao.persist(sale);
					}
				}
			}
		}
	}

	private synchronized void handleLimitOrders() {

		List<MarketOrder> limitBuys = dao.getLimitBuys();
		List<MarketOrder> limitSells = dao.getLimitSells();

		if (limitBuys.size() > 0 && limitSells.size() > 0) {
			for (int buyCt = 0; buyCt < limitBuys.size(); buyCt++) {
				int price = limitBuys.get(buyCt).getPrice();
				int buyAmount = limitBuys.get(buyCt).getAmount();
				for (int sellCt = 0; sellCt < limitBuys.size(); sellCt++) {
					if (price == limitSells.get(sellCt).getPrice()) {
						long time = new Date().getTime();
						MarketOrder limitSell = limitSells.get(sellCt);
						MarketOrder limitBuy = limitBuys.get(buyCt);
						int sellAmount = limitSell.getAmount();
						int smallerAmount;
						if (buyAmount > sellAmount)
							smallerAmount = sellAmount;
						else
							smallerAmount = buyAmount;
						limitBuy.fulfillOrder(smallerAmount);
						limitSell.fulfillOrder(smallerAmount);
						dao.merge(limitBuy);
						dao.merge(limitSell);
						
						Sale sale = new Sale(limitBuy.getClient(),
								limitSell.getClient(), smallerAmount, price, time,
								limitBuy.getId(), limitSell.getId());
						saleDao.persist(sale);
					}

				}
			}
		}
	}

}

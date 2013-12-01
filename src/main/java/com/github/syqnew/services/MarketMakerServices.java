package com.github.syqnew.services;

import java.util.Date;
import java.util.List;

import com.github.syqnew.dao.ClientDao;
import com.github.syqnew.dao.MarketOrderDao;
import com.github.syqnew.dao.MetadataDao;
import com.github.syqnew.dao.QuoteDao;
import com.github.syqnew.dao.SaleDao;
import com.github.syqnew.dao.impl.ClientDaoImpl;
import com.github.syqnew.dao.impl.MarketOrderDaoImpl;
import com.github.syqnew.dao.impl.MetadataDaoImpl;
import com.github.syqnew.dao.impl.QuoteDaoImpl;
import com.github.syqnew.dao.impl.SaleDaoImpl;
import com.github.syqnew.domain.Client;
import com.github.syqnew.domain.MarketOrder;
import com.github.syqnew.domain.Metadata;
import com.github.syqnew.domain.Quote;
import com.github.syqnew.domain.Sale;

/**
 * This Service Object is no longer used because we have transitioned
 * to a model where the orders are handled when a new order has been 
 * put in. 
 * 
 * This class is merely here for reference for the OrdersServices.
 * 
 * @author snew
 *
 */
public class MarketMakerServices {

	MarketOrderDao dao;
	QuoteDao quoteDao;
	ClientDao clientDao;
	SaleDao saleDao;
	MetadataDao metadataDao;

	public MarketMakerServices() {
		dao = new MarketOrderDaoImpl();
		quoteDao = new QuoteDaoImpl();
		clientDao = new ClientDaoImpl();
		saleDao = new SaleDaoImpl();
		metadataDao = new MetadataDaoImpl();
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
		Metadata metadata = metadataDao.findAll().get(0);
		
		Quote quote = new Quote(currentTime);
		if (sellList.size() > 0) {
			MarketOrder sell = sellList.get(0);
			quote.setAsk(sell.getPrice());
			quote.setAskSize(sell.getUnfulfilled());
			metadata.setAsk(sell.getPrice());
			metadata.setAskSize(sell.getUnfulfilled());
		} else {
			metadata.setAsk(-1);
			metadata.setAskSize(0);
		}
		if (buyList.size() > 0) {
			MarketOrder buy = buyList.get(0);
			quote.setBid(buy.getPrice());
			quote.setBidSize(buy.getAmount());
			metadata.setBid(buy.getPrice());
			metadata.setBidSize(buy.getUnfulfilled());
		} else {
			metadata.setBid(-1);
			metadata.setBidSize(0);
		}
		quoteDao.persist(quote);
		metadataDao.merge(metadata);
	}

	private synchronized void handleMarketOrders() {
		return;
//		List<MarketOrder> marketOrders = dao.getMarketOrders();
//		Metadata metadata = metadataDao.findAll().get(0);
//		
//		if (marketOrders.size() > 0) {
//			// get earliest MarketOrder
//			MarketOrder currentOrder = marketOrders.get(0);
//			int type = currentOrder.getOrderType();
//			int size = currentOrder.getUnfulfilled();
//			int marketclientId = currentOrder.getClient();
//			long currentTime = new Date().getTime();
//			List<MarketOrder> sellList = dao.getLimitSells();
//			List<MarketOrder> buyList = dao.getLimitBuys();
//			// Market buy
//			if (type == 1) {
//				if (sellList.size() > 0) {
//					MarketOrder bestAsk = sellList.get(0);
//					int ask = bestAsk.getPrice();
//					int askSize = bestAsk.getUnfulfilled();
//					int askClient = bestAsk.getClient();
//					int smallerAmount;
//					if (size > askSize)
//						smallerAmount = askSize;
//					else
//						smallerAmount = size;
//
//					bestAsk.fulfillOrder(smallerAmount);
//					currentOrder.fulfillOrder(smallerAmount);
//		.ntOrder);
//
//					Quote quote = new Quote(currentTime);
//					if (smallerAmount > 0) {
//						quote.setAsk(ask);
//					} else {
//						quote.setAsk(-1);
//					}
//					quote.setAskSize(smallerAmount);
//					if (buyList.size() > 0) {
//						MarketOrder bidOrder = buyList.get(0);
//						int bid = bidOrder.getPrice();
//						int bidSize = bidOrder.getUnfulfilled();
//						quote.setBid(bid);
//						quote.setBidSize(bidSize);
//					} else {
//						quote.setBid(-1);
//						quote.setBidSize(0);
//					}
//					quoteDao.persist(quote);
//					
//
//					Client marketClient = clientDao.findById(marketclientId);
//					Client limitClient = clientDao.findById(askClient);
//					marketClient.buyShares(smallerAmount, ask * smallerAmount);
//					limitClient.sellShares(smallerAmount, ask * smallerAmount);
//					clientDao.merge(marketClient);
//					clientDao.merge(limitClient);
//
//					if (smallerAmount > 0) {
//						Sale sale = new Sale(marketclientId, askClient,
//								smallerAmount, ask, currentTime,
//								currentOrder.getId(), bestAsk.getId());
//						saleDao.persist(sale);
//					}
//					metadata.addToVolume(smallerAmount);
//					if (metadata.getHigh() < ask)
//						metadata.setHigh(ask);
//					if (metadata.getLow() > ask)
//						metadata.setLow(ask);
//					metadata.setLast(ask);
//					metadataDao.merge(metadata);
//				}
//
//			} else {
//				if (buyList.size() > 0) {
//					MarketOrder bestBid = buyList.get(0);
//					int bid = bestBid.getPrice();
//					int bidSize = bestBid.getUnfulfilled();
//					int bidClient = bestBid.getClient();
//					int smallerAmount;
//
//					if (size > bidSize)
//						smallerAmount = bidSize;
//					else
//						smallerAmount = size;
//
//					bestBid.fulfillOrder(smallerAmount);
//					currentOrder.fulfillOrder(smallerAmount);
//					dao.merge(bestBid);
//					dao.merge(currentOrder);
//
//					Quote quote = new Quote(currentTime);
//					if (smallerAmount > 0) {
//						quote.setBid(bid);
//					} else {
//						quote.setBid(-1);
//					}
//					quote.setBidSize(smallerAmount);
//					if (sellList.size() > 0) {
//						MarketOrder askOrder = sellList.get(0);
//						int ask = askOrder.getPrice();
//						int askSize = askOrder.getUnfulfilled();
//						quote.setAsk(ask);
//						quote.setAskSize(askSize);
//					} else {
//						quote.setAsk(-1);
//						quote.setAskSize(0);
//					}
//					quoteDao.persist(quote);
//
//					Client marketClient = clientDao.findById(marketclientId);
//					Client limitClient = clientDao.findById(bidClient);
//					marketClient.sellShares(smallerAmount, bid * smallerAmount);
//					limitClient.buyShares(smallerAmount, bid * smallerAmount);
//					clientDao.merge(marketClient);
//					clientDao.merge(limitClient);
//
//					if (smallerAmount > 0) {
//						Sale sale = new Sale(marketclientId, bidClient,
//								smallerAmount, bid, currentTime,
//								bestBid.getId(), currentOrder.getId());
//						saleDao.persist(sale);
//					}
//					
//					metadata.addToVolume(smallerAmount);
//					if (metadata.getHigh() < bid)
//						metadata.setHigh(bid);
//					if (metadata.getLow() > bid)
//						metadata.setLow(bid);
//					metadata.setLast(bid);
//					metadataDao.merge(metadata);
//				}
//			}
//		}
	}

	private synchronized void handleLimitOrders() {

		List<MarketOrder> limitBuys = dao.getLimitBuys();
		List<MarketOrder> limitSells = dao.getLimitSells();
		Metadata metadata = metadataDao.findAll().get(0);

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
						
						Client buyClient = clientDao.findById(limitBuy.getClient());
						Client sellClient = clientDao.findById(limitSell.getClient());
						buyClient.buyShares(smallerAmount, price * smallerAmount);
						sellClient.sellShares(smallerAmount, price * smallerAmount);
						clientDao.merge(buyClient);
						clientDao.merge(sellClient);
						
						Sale sale = new Sale(limitBuy.getClient(),
								limitSell.getClient(), smallerAmount, price, time,
								limitBuy.getId(), limitSell.getId());
						saleDao.persist(sale);
						
						metadata.addToVolume(smallerAmount);
						if (metadata.getHigh() < price) metadata.setHigh(price);
						if (metadata.getLow() > price) metadata.setLow(price);
						metadata.setLast(price);
						metadataDao.merge(metadata);
					}

				}
			}
		}
	}

}

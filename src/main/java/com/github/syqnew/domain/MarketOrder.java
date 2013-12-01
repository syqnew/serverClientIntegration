package com.github.syqnew.domain;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * The MarketOrder class represents the MarketOrder table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * A MarketOrder object represents a market or limit order. The table 
 * represents the order book. 
 * 
 * This table actually contains both market and limit orders. 
 * However, order is a reserved word in mySQL.
 * 
 * @author snew
 *
 */
@Entity
public class MarketOrder implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private int orderType;
	private int status;
	private int client;
	private int amount;
	private int price;
	private int unfulfilled;
	private long time;
	@Version
	private int version;

	/**
	 * Empty constructor for Hibernate and Jackson
	 */
	public MarketOrder() {
	}

	/**
	 * Creates a MarketOrder Object
	 * @param orderType; must be 1, 2, 3, or 4
	 * @param status; must be 0, 5, or 10
	 * @param client
	 * @param amount; must be positive
	 * @param price; must be positive
	 * @param unfulfilled; must be <= amount
	 * @param time; must be positive
	 */
	public MarketOrder(int orderType, int status, int client, int amount,
			int price, int unfulfilled, long time) {
		this.orderType = orderType;
		this.status = status;
		this.client = client;
		this.amount = amount;
		this.price = price;
		this.unfulfilled = unfulfilled;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getUnfulfilled() {
		return unfulfilled;
	}

	public void setUnfulfilled(int unfulfilled) {
		this.unfulfilled = unfulfilled;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * Fulfill an order
	 * 
	 * @param size; must be greater than zero
	 */
	public void fulfillOrder(int size) {
		assert size > 0;
		this.unfulfilled -= size;
		if (this.unfulfilled == 0) {
			this.status = 5;
		}
	}

	/**
	 * Cancel the order
	 */
	public void cancelOrder() {
		this.status = 10;
	}

}

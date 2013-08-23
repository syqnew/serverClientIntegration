package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	private int fulfilled;
	private long time;

	public MarketOrder() {
	}

	public MarketOrder(int orderType, int status, int client, int amount,
			int price, int fulfilled, long time) {
		this.orderType = orderType;
		this.status = status;
		this.client = client;
		this.amount = amount;
		this.price = price;
		this.fulfilled = fulfilled;
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

	public int getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(int fulfilled) {
		this.fulfilled = fulfilled;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}

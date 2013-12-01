package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Sale class represents the Sale table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * A Sale object records a transaction that happens between 
 * two clients.
 * 
 * @author snew
 */
@Entity
public class Sale implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private int buyerId;
	private int sellerId;
	private int amount;
	private int price;
	private long time;
	private int buyOrderId;
	private int sellOrderId;

	/**
	 * Empty constructor for Hibernate and Jackson
	 */
	public Sale() {
	}

	/**
	 * Creates a Sale Object
	 * @param buyerId
	 * @param sellerId
	 * @param amount; must be positive
	 * @param price; must be positive
	 * @param time; must be positive
	 * @param buyOrderId
	 * @param sellOrderId
	 */
	public Sale(int buyerId, int sellerId, int amount, int price, long time,
			int buyOrderId, int sellOrderId) {
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.amount = amount;
		this.price = price;
		this.time = time;
		this.buyOrderId = buyOrderId;
		this.sellOrderId = sellOrderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getBuyOrderId() {
		return buyOrderId;
	}

	public void setBuyOrderId(int buyOrderId) {
		this.buyOrderId = buyOrderId;
	}

	public int getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

}

package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Client class represents the Client table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * Each user has a client associated with them. The client object/row
 * keeps track of the number of shares and amount of cash a user has. 
 * 
 * @author snew
 */
@Entity
public class Client implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private String email;
	private int cash = 10000;
	private int shares = 400;

	/**
	 * Empty constructor for Hibernate and Jackson
	 */
	public Client() {
	}

	/**
	 * Creates a client object
	 * 
	 * @param String email; must be nonempty
	 */
	public Client(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	/**
	 * Client buys a certain number of shares at a certain price
	 * 
	 * @param shares; must be positive
	 * @param totalPrice; must be nonnegative
	 */
	public void buyShares(int shares, int totalPrice) {
		assert shares > 0;
		assert totalPrice >= 0;
		this.shares += shares;
		this.cash -= totalPrice;
	}

	/**
	 * Client sells a certain number of shares at a certain price
	 * 
	 * @param shares; must be positive
	 * @param totalPrice; must be nonnegative
	 */
	public void sellShares(int shares, int totalPrice) {
		assert shares > 0;
		assert totalPrice >= 0;
		this.shares -= shares;
		this.cash += totalPrice;
	}

}

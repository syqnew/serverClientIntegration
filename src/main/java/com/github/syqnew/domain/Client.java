package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private String email;
	private int cash = 100000;
	private int shares = 400;

	public Client() {
	}

	// Each client starts out with $10,000 and 400 shares
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

	public void buyShares(int shares, int totalPrice) {
		this.shares += shares;
		this.cash -= totalPrice;
	}

	public void sellShares(int shares, int totalPrice) {
		this.shares -= shares;
		this.cash += totalPrice;
	}

}

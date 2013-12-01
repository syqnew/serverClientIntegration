package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Quote class represents the Quote table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * A quote object represents a bid or ask quote.
 * 
 * @author snew
 */
@Entity
public class Quote implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private int bid;
	private int bidSize;
	private int ask;
	private int askSize;
	private long time;

	/** 
	 * Empty constructor for Hibernate and Jackson
	 */
	public Quote() {
	}

	/**
	 * Creates a Quote object
	 * @param time; must be positive
	 */
	public Quote(long time) {
		super();
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public int getAsk() {
		return ask;
	}

	public void setAsk(int ask) {
		this.ask = ask;
	}

	public int getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}

package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * The Metadata class represents the Metadata table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * This table is used primarily for quick access to Market Stats, like
 * current ask, bid, last selling price, etc. 
 * 
 * Only one row is in this table at one time and the row is automatically 
 * inserted when the table is created, so there is no need for a nonempty
 * constructor.
 * 
 * @author snew
 */
@Entity
public class Metadata implements BaseObject {

	@Id
	private int id;
	private int last;
	private int high;
	private int low;
	private int volume;
	private int bid;
	private int bidSize;
	private int ask;
	private int askSize;
	@Version
	private int version;

	/**
	 * Empty constructor for Hibernate and Jackson
	 */
	public Metadata() {
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * Add stocks to the total volume
	 * @param size; must be greater than zero
	 */
	public void addToVolume(int size) {
		assert size > 0;
		this.volume += size;
	}

}

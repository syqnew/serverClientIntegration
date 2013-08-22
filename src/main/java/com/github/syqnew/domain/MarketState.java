package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MarketState implements BaseObject {

	@Id
	private int year;
	private String year1Event;
	private String year2Event;

	public MarketState() {
	}

	public MarketState(int year, String year1Event, String year2Event) {
		this.year = year;
		this.year1Event = year1Event;
		this.year2Event = year2Event;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getYear1Event() {
		return year1Event;
	}

	public void setYear1Event(String year1Event) {
		this.year1Event = year1Event;
	}

	public String getYear2Event() {
		return year2Event;
	}

	public void setYear2Event(String year2Event) {
		this.year2Event = year2Event;
	}

}

package com.github.syqnew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The News class represents the News table in the database. 
 * Annotations are for Hibernate's ORM.
 * 
 * A News object contains a message that contains information 
 * about the market.
 * 
 * @author snew
 */
@Entity
public class News implements BaseObject {

	@Id
	@GeneratedValue
	private int id;
	private String message;

	/**
	 * Empty constructor for Hibernate and Jackson
	 */
	public News() {
	}

	/**
	 * Creates a News object
	 * @param message; must be nonempty
	 */
	public News(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package com.publishers.model;

public class Publisher implements java.io.Serializable {
	private String publisher_ID;
	private String publisher_Name;
	private String publisher_Phone;
	private String publisher_Address;
	private String publisher_Email;

	public String getPublisher_ID() {
		return publisher_ID;
	}

	public void setPublisher_ID(String publisher_ID) {
		this.publisher_ID = publisher_ID;
	}

	public String getPublisher_Name() {
		return publisher_Name;
	}

	public void setPublisher_Name(String publisher_Name) {
		this.publisher_Name = publisher_Name;
	}

	public String getPublisher_Phone() {
		return publisher_Phone;
	}

	public void setPublisher_Phone(String publisher_Phone) {
		this.publisher_Phone = publisher_Phone;
	}

	public String getPublisher_Address() {
		return publisher_Address;
	}

	public void setPublisher_Address(String publisher_Address) {
		this.publisher_Address = publisher_Address;
	}

	public String getPublisher_Email() {
		return publisher_Email;
	}

	public void setPublisher_Email(String publisher_Email) {
		this.publisher_Email = publisher_Email;
	}

	public Publisher() {

	}

	public Publisher(String publisher_ID, String publisher_Name, String publisher_Phone, String publisher_Address,
			String publisher_Email) {
		this.publisher_ID = publisher_ID;
		this.publisher_Name = publisher_Name;
		this.publisher_Phone = publisher_Phone;
		this.publisher_Address = publisher_Address;
		this.publisher_Email = publisher_Email;
	}

}

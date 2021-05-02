package com.rev.model;

import java.sql.Timestamp;

public class RevVO implements java.io.Serializable {
	private String rev_id;
	private String rev_content;
	private Timestamp rev_date;
	private String mem_id;
	private String book_id;
	private Integer rating;
	private Integer rev_status;
	private String mem_name;
	
	private String timeForJson;
	
	
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	
	
	public String getRev_id() {
		return rev_id;
	}
	public void setRev_id(String rev_id) {
		this.rev_id = rev_id;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public Timestamp getRev_date() {
		return rev_date;
	}
	public void setRev_date(Timestamp rev_date) {
		this.rev_date = rev_date;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public Integer getRev_status() {
		return rev_status;
	}
	public void setRev_status(Integer rev_status) {
		this.rev_status = rev_status;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getTimeForJson() {
		return timeForJson;
	}
	public void setTimeForJson(String timeForJson) {
		this.timeForJson = timeForJson;
	}
}

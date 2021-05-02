package com.favorite_book.model;

import java.util.Date;

public class Favorite_Book implements java.io.Serializable {
	private String book_ID;
	private String mem_ID;
	private Date fav_Time;
	
	public String getBook_ID() {
		return book_ID;
	}

	public void setBook_ID(String book_ID) {
		this.book_ID = book_ID;
	}

	public String getMem_ID() {
		return mem_ID;
	}

	public void setMem_ID(String mem_ID) {
		this.mem_ID = mem_ID;
	}

	public Date getFav_Time() {
		return fav_Time;
	}

	public void setFav_Time(Date fav_Time) {
		this.fav_Time = fav_Time;
	}
	
	
}

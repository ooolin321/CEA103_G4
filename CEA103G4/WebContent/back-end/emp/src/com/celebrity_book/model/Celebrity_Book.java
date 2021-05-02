package com.celebrity_book.model;

public class Celebrity_Book implements java.io.Serializable {
	private String book_ID;
	private String mem_ID;
	private Integer share_State;

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
	public Integer getShare_State() {
		return share_State;
	}
	public void setShare_State(Integer share_State) {
		this.share_State = share_State;
	}
	
	
}

package com.detail.model;

public class DetailVO {

	private String order_id;
	private String book_id;
	private String items_name;
	private Integer comm_qty;
	private Double comm_price;
	private Integer comm_bonus;
	
	
	public DetailVO(){
		super();
	}
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getItems_name() {
		return items_name;
	}
	public void setItems_name(String items_name) {
		this.items_name = items_name;
	}
	public Integer getComm_qty() {
		return comm_qty;
	}
	public void setComm_qty(Integer comm_qty) {
		this.comm_qty = comm_qty;
	}
	public Double getComm_price() {
		return comm_price;
	}
	public void setComm_price(Double comm_price) {
		this.comm_price = comm_price;
	}
	public Integer getComm_bonus() {
		return comm_bonus;
	}
	public void setComm_bonus(Integer comm_bonus) {
		this.comm_bonus = comm_bonus;
	}
	
	
	
	
	
}

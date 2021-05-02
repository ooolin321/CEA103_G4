package com.udbtx.model;

import java.sql.*;

public class UdbTxVO implements java.io.Serializable {
	private String po_no;
	private String book_id;
	private String seller_mem_id;
	private String buyer_mem_id;
	private String book_state;
	private byte[] book_state_pic;
	private Integer udb_sale_price;
	private Timestamp release_date;
	private Integer udb_order_state_no;
	private Timestamp udb_order_date;
	private Integer prod_state;
	private Integer payment_state;

	public UdbTxVO() {
	}

	public UdbTxVO(String po_no, String book_id, String seller_mem_id, String buyer_mem_id,
			String book_state, byte[] book_state_pic, Integer udb_sale_price, Timestamp release_date,
			Integer udb_order_state_no, Timestamp udb_order_date, Integer prod_state, Integer payment_state) {
		this.po_no = po_no;
		this.book_id = book_id;
		this.seller_mem_id = seller_mem_id;
		this.buyer_mem_id = buyer_mem_id;
		this.book_state = book_state;
		this.book_state_pic = book_state_pic;
		this.udb_sale_price = udb_sale_price;
		this.release_date = release_date;
		this.udb_order_state_no = udb_order_state_no;
		this.udb_order_date = udb_order_date;
		this.prod_state = prod_state;
		this.payment_state = payment_state;

	}

	public String getPo_no() {
		return po_no;
	}

	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	
	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getSeller_mem_id() {
		return seller_mem_id;
	}

	public void setSeller_mem_id(String seller_mem_id) {
		this.seller_mem_id = seller_mem_id;
	}

	public String getBuyer_mem_id() {
		return buyer_mem_id;
	}

	public void setBuyer_mem_id(String buyer_mem_id) {
		this.buyer_mem_id = buyer_mem_id;
	}

	public String getBook_state() {
		return book_state;
	}

	public void setBook_state(String book_state) {
		this.book_state = book_state;
	}

	public byte[] getBook_state_pic() {
		return book_state_pic;
	}

	public void setBook_state_pic(byte[] book_state_pic) {
		this.book_state_pic = book_state_pic;
	}

	public Integer getUdb_sale_price() {
		return udb_sale_price;
	}

	public void setUdb_sale_price(Integer udb_sale_price) {
		this.udb_sale_price = udb_sale_price;
	}

	public Timestamp getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Timestamp release_date) {
		this.release_date = release_date;
	}

	public Integer getUdb_order_state_no() {
		return udb_order_state_no;
	}

	public void setUdb_order_state_no(Integer udb_order_state_no) {
		this.udb_order_state_no = udb_order_state_no;
	}

	public Timestamp getUdb_order_date() {
		return udb_order_date;
	}

	public void setUdb_order_date(Timestamp udb_order_date) {
		this.udb_order_date = udb_order_date;
	}

	public Integer getProd_state() {
		return prod_state;
	}

	public void setProd_state(Integer prod_state) {
		this.prod_state = prod_state;
	}

	public Integer getPayment_state() {
		return payment_state;
	}

	public void setPayment_state(Integer payment_state) {
		this.payment_state = payment_state;
	}
	

}

package com.product.model;
import java.sql.Blob;
import java.sql.Date;

public class ProductVO implements java.io.Serializable{
	private Integer product_no;
	private String product_name;
	private String product_info;
	private Integer product_price;
	private Integer product_quantity;
	private Integer product_remaining;
	private Integer product_state;
	private byte[] product_photo;
	private String user_id;
	private Integer pdtype_no;
	private Integer start_price;
	private Integer live_no;
	
	public Integer getProduct_no() {
		return product_no;
	}
	public void setProduct_no(Integer product_no) {
		this.product_no = product_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_info() {
		return product_info;
	}
	public void setProduct_info(String product_info) {
		this.product_info = product_info;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(Integer product_quantity) {
		this.product_quantity = product_quantity;
	}
	public Integer getProduct_remaining() {
		return product_remaining;
	}
	public void setProduct_remaining(Integer product_remaining) {
		this.product_remaining = product_remaining;
	}
	public Integer getProduct_state() {
		return product_state;
	}
	public void setProduct_state(Integer product_state) {
		this.product_state = product_state;
	}
	public byte[] getProduct_photo() {
		return product_photo;
	}
	public void setProduct_photo(byte[] product_photo) {
		this.product_photo = product_photo;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getPdtype_no() {
		return pdtype_no;
	}
	public void setPdtype_no(Integer pdtype_no) {
		this.pdtype_no = pdtype_no;
	}
	public Integer getStart_price() {
		return start_price;
	}
	public void setStart_price(Integer start_price) {
		this.start_price = start_price;
	}
	public Integer getLive_no() {
		return live_no;
	}
	public void setLive_no(Integer live_no) {
		this.live_no = live_no;
	}
}

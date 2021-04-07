package com.pdc.model;
import java.sql.Date;

public class PdcVO implements java.io.Serializable{	
	private Integer product_id;
	private String product_name;
	private String product_info;
	private Integer product_price;
	private Integer product_quantity;
	private Integer product_remaining;
	private String product_state;
	private Byte product_photo;
	private String user_id;
	private String pdtype_id;
	private Integer start_price;
	private Integer live_id;

	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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
	public String getProduct_state() {
		return product_state;
	}
	public void setProduct_state(String product_state) {
		this.product_state = product_state;
	}
	public Byte getProduct_photo() {
		return product_photo;
	}
	public void setProduct_photo(Byte product_photo) {
		this.product_photo = product_photo;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPdtype_id() {
		return pdtype_id;
	}
	public void setPdtype_id(String pdtype_id) {
		this.pdtype_id = pdtype_id;
	}
	public Integer getStart_price() {
		return start_price;
	}
	public void setStart_price(Integer start_price) {
		this.start_price = start_price;
	}
	public Integer getLive_id() {
		return live_id;
	}
	public void setLive_id(Integer live_id) {
		this.live_id = live_id;
	}


	
	
}

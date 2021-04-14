package com.live_order.model;

import java.sql.Date;

public class Live_orderVO {
	private Integer live_order_no;
	private Date order_date;
	private Integer order_state;
	private Integer order_shipping;
	private Integer order_price;
	private Integer pay_method;
	private Date pay_deadline;
	private String rec_name;
	private String rec_addr;
	private Integer rec_phone;
	private Integer rec_cellphone;
	private Integer logistics;
	private Integer logistics_state;
	private Integer discount;
	private Integer live_no;
	private String user_id;
	private String seller_id;
	private Integer srating;
	private String srating_content;
	private Integer point;
	public Integer getLive_order_no() {
		return live_order_no;
	}
	public void setLive_order_no(Integer live_order_no) {
		this.live_order_no = live_order_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public Integer getOrder_state() {
		return order_state;
	}
	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}
	public Integer getOrder_shipping() {
		return order_shipping;
	}
	public void setOrder_shipping(Integer order_shipping) {
		this.order_shipping = order_shipping;
	}
	public Integer getOrder_price() {
		return order_price;
	}
	public void setOrder_price(Integer order_price) {
		this.order_price = order_price;
	}
	public Integer getPay_method() {
		return pay_method;
	}
	public void setPay_method(Integer pay_method) {
		this.pay_method = pay_method;
	}
	public Date getPay_deadline() {
		return pay_deadline;
	}
	public void setPay_deadline(Date pay_deadline) {
		this.pay_deadline = pay_deadline;
	}
	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
	public String getRec_addr() {
		return rec_addr;
	}
	public void setRec_addr(String rec_addr) {
		this.rec_addr = rec_addr;
	}
	public Integer getRec_phone() {
		return rec_phone;
	}
	public void setRec_phone(Integer rec_phone) {
		this.rec_phone = rec_phone;
	}
	public Integer getRec_cellphone() {
		return rec_cellphone;
	}
	public void setRec_cellphone(Integer rec_cellphone) {
		this.rec_cellphone = rec_cellphone;
	}
	public Integer getLogistics() {
		return logistics;
	}
	public void setLogistics(Integer logistics) {
		this.logistics = logistics;
	}
	public Integer getLogistics_state() {
		return logistics_state;
	}
	public void setLogistics_state(Integer logistics_state) {
		this.logistics_state = logistics_state;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getLive_no() {
		return live_no;
	}
	public void setLive_no(Integer live_no) {
		this.live_no = live_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public Integer getSrating() {
		return srating;
	}
	public void setSrating(Integer srating) {
		this.srating = srating;
	}
	public String getSrating_content() {
		return srating_content;
	}
	public void setSrating_content(String srating_content) {
		this.srating_content = srating_content;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
}

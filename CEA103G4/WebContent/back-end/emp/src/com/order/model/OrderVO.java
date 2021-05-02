package com.order.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class OrderVO implements Serializable {
	
	private String order_id;
	private String mem_id;
	private String rec_name;
	private String rec_tel;
	private String rec_add;
	private Timestamp order_date;
	private Integer order_qty;
	private Integer order_total;
	private Integer order_pay;
	private Integer delivery;
	private Integer get_bonus;
	private Integer use_bonus;
	private Integer order_status;
	private String mem_note;
	
	
	public OrderVO() {
		super();
	}


	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public String getMem_id() {
		return mem_id;
	}


	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}


	public String getRec_name() {
		return rec_name;
	}


	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}


	public String getRec_tel() {
		return rec_tel;
	}


	public void setRec_tel(String rec_tel) {
		this.rec_tel = rec_tel;
	}


	public String getRec_add() {
		return rec_add;
	}


	public void setRec_add(String rec_add) {
		this.rec_add = rec_add;
	}


	public Integer getOrder_qty() {
		return order_qty;
	}


	public void setOrder_qty(Integer order_qty) {
		this.order_qty = order_qty;
	}


	public Integer getOrder_total() {
		return order_total;
	}


	public void setOrder_total(Integer order_total) {
		this.order_total = order_total;
	}


	public Integer getOrder_pay() {
		return order_pay;
	}


	public void setOrder_pay(Integer order_pay) {
		this.order_pay = order_pay;
	}


	public Integer getDelivery() {
		return delivery;
	}


	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}


	public Integer getOrder_status() {
		return order_status;
	}


	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}


	public String getMem_note() {
		return mem_note;
	}


	public void setMem_note(String mem_note) {
		this.mem_note = mem_note;
	}


	public Integer getGet_bonus() {
		return get_bonus;
	}


	public void setGet_bonus(Integer get_bonus) {
		this.get_bonus = get_bonus;
	}


	public Integer getUse_bonus() {
		return use_bonus;
	}


	public void setUse_bonus(Integer use_bonus) {
		this.use_bonus = use_bonus;
	}


	public Timestamp getOrder_date() {
		return order_date;
	}


	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	
	
}

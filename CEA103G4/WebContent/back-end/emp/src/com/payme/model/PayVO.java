package com.payme.model;

public class PayVO implements java.io.Serializable{
	private String credit_card_num;
	private Integer credit_card_year;
	private Integer credit_card_mon;
	private String mem_id;
	
	public String getCredit_card_num() {
		return credit_card_num;
	}
	public void setCredit_card_num(String credit_card_num) {
		this.credit_card_num = credit_card_num;
	}
	public Integer getCredit_card_year() {
		return credit_card_year;
	}
	public void setCredit_card_year(Integer credit_card_year) {
		this.credit_card_year = credit_card_year;
	}
	public Integer getCredit_card_mon() {
		return credit_card_mon;
	}
	public void setCredit_card_mon(Integer credit_card_mon) {
		this.credit_card_mon = credit_card_mon;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
}

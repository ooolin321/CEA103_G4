package com.bookclub_regis_detail.model;

import java.sql.Timestamp;

public class BookClub_Regis_DetailVO implements java.io.Serializable{
	private String brd_id;
	private String mem_id;
	private String bc_id;
	private Integer brd_status;
	
	
	public String getBrd_id() {
		return brd_id;
	}
	public void setBrd_id(String brd_id) {
		this.brd_id = brd_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getBc_id() {
		return bc_id;
	}
	public void setBc_id(String bc_id) {
		this.bc_id = bc_id;
	}
	public Integer getBrd_status() {
		return brd_status;
	}
	public void setBrd_status(Integer brd_status) {
		this.brd_status = brd_status;
	}
	public Timestamp getBrd_date() {
		return brd_date;
	}
	public void setBrd_date(Timestamp brd_date) {
		this.brd_date = brd_date;
	}
	private Timestamp brd_date;
	
	
	
	
	
	

}

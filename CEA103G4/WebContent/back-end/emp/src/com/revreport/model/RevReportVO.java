package com.revreport.model;

import java.sql.Timestamp;

public class RevReportVO implements java.io.Serializable{
	private String rev_rep_id;
	private String rev_id;
	private String mem_id;
	private Integer rev_rep_status;
	private String rev_rep_reason;
	private byte[] rev_rep_proof;
	private Timestamp rev_rep_date;
	
	public String getRev_rep_id() {
		return rev_rep_id;
	}
	public void setRev_rep_id(String rev_rep_id) {
		this.rev_rep_id = rev_rep_id;
	}
	public String getRev_id() {
		return rev_id;
	}
	public void setRev_id(String rev_id) {
		this.rev_id = rev_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getRev_rep_status() {
		return rev_rep_status;
	}
	public void setRev_rep_status(Integer rev_rep_status) {
		this.rev_rep_status = rev_rep_status;
	}
	public String getRev_rep_reason() {
		return rev_rep_reason;
	}
	public void setRev_rep_reason(String rev_rep_reason) {
		this.rev_rep_reason = rev_rep_reason;
	}
	public byte[] getRev_rep_proof() {
		return rev_rep_proof;
	}
	public void setRev_rep_proof(byte[] rev_rep_proof) {
		this.rev_rep_proof = rev_rep_proof;
	}
	public Timestamp getRev_rep_date() {
		return rev_rep_date;
	}
	public void setRev_rep_date(Timestamp rev_rep_date) {
		this.rev_rep_date = rev_rep_date;
	}
	
	
}

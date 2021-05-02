package com.bookclub_report.model;

import java.sql.Timestamp;

public class BookClub_ReportVO implements java.io.Serializable{
	private String bcr_id;
	private String mem_id;
	private String bc_id;
	private Integer bcr_status;
	private String bcr_reason;
	private byte[] bcr_proof;
	private Timestamp bcr_date;
	
	public String getBcr_id() {
		return bcr_id;
	}
	public void setBcr_id(String bcr_id) {
		this.bcr_id = bcr_id;
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
	public Integer getBcr_status() {
		return bcr_status;
	}
	public void setBcr_status(Integer bcr_status) {
		this.bcr_status = bcr_status;
	}
	public String getBcr_reason() {
		return bcr_reason;
	}
	public void setBcr_reason(String bcr_reason) {
		this.bcr_reason = bcr_reason;
	}
	public byte[] getBcr_proof() {
		return bcr_proof;
	}
	public void setBcr_proof(byte[] bcr_proof) {
		this.bcr_proof = bcr_proof;
	}
	public Timestamp getBcr_date() {
		return bcr_date;
	}
	public void setBcr_date(Timestamp bcr_date) {
		this.bcr_date = bcr_date;
	}
	
	
	
	
	
	
	

}

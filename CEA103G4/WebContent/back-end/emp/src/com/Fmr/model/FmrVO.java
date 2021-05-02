package com.Fmr.model;

import java.sql.Timestamp;

public class FmrVO implements java.io.Serializable{ // p167 JavaBean :1.public 類別並不傳入參數的建構子  2. get,set方法  3. 是可序列化的(implements java.io.Serializable)
	private String fmrId;
	private String fmId;
	private String memId;
	private Integer fmrStatus;
	private String fmrContent;
	private Timestamp fmrDate;
	private String adminId;
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getFmrId() {
		return fmrId;
	}
	public void setFmrId(String fmrId) {
		this.fmrId = fmrId;
	}
	public String getFmId() {
		return fmId;
	}
	public void setFmId(String fmId) {
		this.fmId = fmId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getFmrStatus() {
		return fmrStatus;
	}
	public void setFmrStatus(Integer fmrStatus) {
		this.fmrStatus = fmrStatus;
	}
	public String getFmrContent() {
		return fmrContent;
	}
	public void setFmrContent(String fmrContent) {
		this.fmrContent = fmrContent;
	}
	public Timestamp getFmrDate() {
		return fmrDate;
	}
	public void setFmrDate(Timestamp fmrDate) {
		this.fmrDate = fmrDate;
	}
	
}

package com.Far.model;

import java.sql.Timestamp;

public class FarVO implements java.io.Serializable{ // p167 JavaBean :1.public 類別並不傳入參數的建構子  2. get,set方法  3. 是可序列化的(implements java.io.Serializable)
	private String farId;
	private String faId;
	private String memId;
	private Integer farStatus;
	private String farContent;
	private Timestamp farDate;
	private String adminId;
	
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getFarId() {
		return farId;
	}
	public void setFarId(String farId) {
		this.farId = farId;
	}
	public String getFaId() {
		return faId;
	}
	public void setFaId(String faId) {
		this.faId = faId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getFarStatus() {
		return farStatus;
	}
	public void setFarStatus(Integer farStatus) {
		this.farStatus = farStatus;
	}
	public String getFarContent() {
		return farContent;
	}
	public void setFarContent(String farContent) {
		this.farContent = farContent;
	}
	public Timestamp getFarDate() {
		return farDate;
	}
	public void setFarDate(Timestamp farDate) {
		this.farDate = farDate;
	}
	
}

package com.Fm.model;

import java.sql.Timestamp;

public class FmVO implements java.io.Serializable{ // p167 JavaBean :1.public 類別並不傳入參數的建構子  2. get,set方法  3. 是可序列化的(implements java.io.Serializable)
	private String fmId;
	private String faId;
	private String memId;
	private String fmContent;
	private Integer fmLike;
	private Integer fmDisLike;
	private Integer fmStatus;
	private Timestamp fmDate;
	public String getFmId() {
		return fmId;
	}
	public void setFmId(String fmId) {
		this.fmId = fmId;
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
	public String getFmContent() {
		return fmContent;
	}
	public void setFmContent(String fmContent) {
		this.fmContent = fmContent;
	}
	public Integer getFmLike() {
		return fmLike;
	}
	public void setFmLike(Integer fmLike) {
		this.fmLike = fmLike;
	}
	public Integer getFmDisLike() {
		return fmDisLike;
	}
	public void setFmDisLike(Integer fmDisLike) {
		this.fmDisLike = fmDisLike;
	}
	public Integer getFmStatus() {
		return fmStatus;
	}
	public void setFmStatus(Integer fmStatus) {
		this.fmStatus = fmStatus;
	}
	public Timestamp getFmDate() {
		return fmDate;
	}
	public void setFmDate(Timestamp fmDate) {
		this.fmDate = fmDate;
	}
	
}

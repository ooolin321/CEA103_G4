package com.Fa.model;

import java.sql.Timestamp;

public class FaVO implements java.io.Serializable{ // p167 JavaBean :1.public 類別並不傳入參數的建構子  2. get,set方法  3. 是可序列化的(implements java.io.Serializable)
	private String faId ;
	private String memId;
	private String faTopic;
	private String faContent;
	private Integer faLike;
	private Integer faDislike;
	private Integer faStatus;
	private Timestamp faDate;
	private Integer faViews;
	
	
	public Integer getFaViews() {
		return faViews;
	}
	public void setFaViews(Integer faViews) {
		this.faViews = faViews;
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
	public String getFaTopic() {
		return faTopic;
	}
	public void setFaTopic(String faTopic) {
		this.faTopic = faTopic;
	}
	public String getFaContent() {
		return faContent;
	}
	public void setFaContent(String faContent) {
		this.faContent = faContent;
	}
	public Integer getFaLike() {
		return faLike;
	}
	public void setFaLike(Integer faLike) {
		this.faLike = faLike;
	}
	public Integer getFaDislike() {
		return faDislike;
	}
	public void setFaDislike(Integer faDislike) {
		this.faDislike = faDislike;
	}
	public Integer getFaStatus() {
		return faStatus;
	}
	public void setFaStatus(Integer faStatus) {
		this.faStatus = faStatus;
	}
	public Timestamp getFaDate() {
		return faDate;
	}
	public void setFaDate(Timestamp faDate) {
		this.faDate = faDate;
	}
	
}

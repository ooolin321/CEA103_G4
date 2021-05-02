package com.cs.model;

import java.sql.Timestamp;
import java.util.Date;

public class CsVO implements java.io.Serializable {
	private String cs_ID;
	private String cs_Email;
	private String cs_Tel;
	private String cs_Subject;
	private String cs_Message;
	private Integer cs_isSend;
	private Timestamp cs_Time;
	
	public String getCs_ID() {
		return cs_ID;
	}
	public void setCs_ID(String cs_ID) {
		this.cs_ID = cs_ID;
	}
	public String getCs_Email() {
		return cs_Email;
	}
	public void setCs_Email(String cs_Email) {
		this.cs_Email = cs_Email;
	}
	public String getCs_Tel() {
		return cs_Tel;
	}
	public void setCs_Tel(String cs_Tel) {
		this.cs_Tel = cs_Tel;
	}
	public String getCs_Subject() {
		return cs_Subject;
	}
	public void setCs_Subject(String cs_Subject) {
		this.cs_Subject = cs_Subject;
	}
	public String getCs_Message() {
		return cs_Message;
	}
	public void setCs_Message(String cs_Message) {
		this.cs_Message = cs_Message;
	}
	public Integer getCs_isSend() {
		return cs_isSend;
	}
	public void setCs_isSend(Integer cs_isSend) {
		this.cs_isSend = cs_isSend;
	}
	public Date getCs_Time() {
		return cs_Time;
	}
	public void setCs_Time(Timestamp cs_Time) {
		this.cs_Time = cs_Time;
	}
	
	
	
	
}

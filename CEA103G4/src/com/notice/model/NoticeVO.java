package com.notice.model;
import java.sql.Date;

public class NoticeVO implements java.io.Serializable{
	private Integer notice_no;
	private String user_id;
	private String content;
	private Date noc_date;
	private Integer noc_state;
	
	public Integer getNotice_no() {
		return notice_no;
	}
	public void setNotice_no(Integer notice_no) {
		this.notice_no = notice_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getNoc_date() {
		return noc_date;
	}
	public void setNoc_date(Date noc_date) {
		this.noc_date = noc_date;
	}
	public Integer getNoc_state() {
		return noc_state;
	}
	public void setNoc_state(Integer noc_state) {
		this.noc_state = noc_state;
	}
}

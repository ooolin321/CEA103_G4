package com.admins.model;

import java.sql.Date;

public class AdminsVO implements java.io.Serializable{
	
	private String admin_id;
	private String admin_name;
	private String admin_id_no;
	private String admin_pswd;
	private String admin_mobile;
	private String admin_address;
	private Date admin_dutydate;
	private Integer admin_jobstate;
	private byte[] admin_pic;
	private String admin_mail;
	
	public AdminsVO() {}
	
	public AdminsVO(String admin_id, String admin_name,String admin_id_no, String admin_pswd, String admin_mobile, 
			        String admin_address, Date admin_dutydate ,Integer admin_jobstate, byte[] admin_pic, String admin_mail) {
		
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_id_no = admin_id_no;
		this.admin_pswd = admin_pswd;
		this.admin_mobile = admin_mobile;
		this.admin_address = admin_address;
		this.admin_dutydate = admin_dutydate;
		this.admin_jobstate = admin_jobstate;
		this.admin_pic = admin_pic;
				
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_id_no() {
		return admin_id_no;
	}

	public void setAdmin_id_no(String admin_id_no) {
		this.admin_id_no = admin_id_no;
	}

	
	public String getAdmin_pswd() {
		return admin_pswd;
	}

	public void setAdmin_pswd(String admin_pswd) {
		this.admin_pswd = admin_pswd;
	}

	public String getAdmin_mobile() {
		return admin_mobile;
	}

	public void setAdmin_mobile(String admin_mobile) {
		this.admin_mobile = admin_mobile;
	}

	public String getAdmin_address() {
		return admin_address;
	}

	public void setAdmin_address(String admin_address) {
		this.admin_address = admin_address;
	}

	public Date getAdmin_dutydate() {
		return admin_dutydate;
	}

	public void setAdmin_dutydate(Date admin_dutydate) {
		this.admin_dutydate = admin_dutydate;
	}

	public Integer getAdmin_jobstate() {
		return admin_jobstate;
	}

	public void setAdmin_jobstate(Integer admin_jobstate) {
		this.admin_jobstate = admin_jobstate;
	}

	public byte[] getAdmin_pic() {
		return admin_pic;
	}

	public void setAdmin_pic(byte[] admin_pic) {
		this.admin_pic = admin_pic;
	}

	public String getAdmin_mail() {
		return admin_mail;
	}

	public void setAdmin_mail(String admin_mail) {
		this.admin_mail = admin_mail;
	}
	

}

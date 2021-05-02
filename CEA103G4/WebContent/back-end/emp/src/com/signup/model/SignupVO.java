package com.signup.model;

import java.sql.Timestamp;

public class SignupVO {
	private String signup_id;
	private String mem_id;
	private String lc_id;
	private Timestamp signup_time;
	
	public String getSignup_id() {
		return signup_id;
	}
	public void setSignup_id(String signup_id) {
		this.signup_id = signup_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getLc_id() {
		return lc_id;
	}
	public void setLc_id(String lc_id) {
		this.lc_id = lc_id;
	}
	public Timestamp getSignup_time() {
		return signup_time;
	}
	public void setSignup_time(Timestamp signup_time) {
		this.signup_time = signup_time;
	}
		
}

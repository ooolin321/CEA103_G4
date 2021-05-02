package com.Follow.model;

public class FollowVO implements java.io.Serializable{  // p167 JavaBean :1.public 類別並不傳入參數的建構子  2. get,set方法  3. 是可序列化的(implements java.io.Serializable)
	private String memId;
	private String aMemId;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getaMemId() {
		return aMemId;
	}
	public void setaMemId(String aMemId) {
		this.aMemId = aMemId;
	}
	
}

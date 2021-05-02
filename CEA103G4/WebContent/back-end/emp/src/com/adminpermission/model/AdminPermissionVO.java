package com.adminpermission.model;

public class AdminPermissionVO implements java.io.Serializable{
	
	private String per_id;
	private String admin_id;
	
	public AdminPermissionVO() {}
	
	public AdminPermissionVO(String per_id, String admin_id) {
		this.per_id = per_id;
		this.admin_id = admin_id;
	}

	public String getPer_id() {
		return per_id;
	}

	public void setPer_id(String per_id) {
		this.per_id = per_id;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

}

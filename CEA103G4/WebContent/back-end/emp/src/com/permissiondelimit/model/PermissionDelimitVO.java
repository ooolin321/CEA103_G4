package com.permissiondelimit.model;

public class PermissionDelimitVO implements java.io.Serializable {

	private String per_id;
	private String per_name;
	private String per_uri;

	public PermissionDelimitVO() {
	}

	public PermissionDelimitVO(String per_id, String per_name) {
		this.per_id = per_id;
		this.per_name = per_name;
	}

	public String getPer_id() {
		return per_id;
	}

	public void setPer_id(String per_id) {
		this.per_id = per_id;
	}

	public String getPer_name() {
		return per_name;
	}

	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}
	
	public String getPer_uri() {
		return per_uri;
	}

	public void setPer_uri(String per_uri) {
		this.per_uri = per_uri;
	}
}

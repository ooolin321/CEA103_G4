package com.adver.model;

import java.sql.Timestamp;

public class AdVO {
	private String ad_id; //notnull
	private Integer blo_class; //default(1)
	private String act_name;
	private String ad_type; //notnull
	private Timestamp ad_start;
	private Timestamp ad_end;
	private byte[] ad_image; //notnull
	private String ad_url;
	private String ad_copy;
	private Timestamp ad_update; //default
	
	
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public Integer getBlo_class() {
		return blo_class;
	}
	public void setBlo_class(Integer blo_class) {
		this.blo_class = blo_class;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getAd_type() {
		return ad_type;
	}
	public void setAd_type(String ad_type) {
		this.ad_type = ad_type;
	}
	public Timestamp getAd_start() {
		return ad_start;
	}
	public void setAd_start(Timestamp ad_start) {
		this.ad_start = ad_start;
	}
	public Timestamp getAd_end() {
		return ad_end;
	}
	public void setAd_end(Timestamp ad_end) {
		this.ad_end = ad_end;
	}
	public byte[] getAd_image() {
		return ad_image;
	}
	public void setAd_image(byte[] ad_image) {
		this.ad_image = ad_image;
	}
	public String getAd_url() {
		return ad_url;
	}
	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}
	public String getAd_copy() {
		return ad_copy;
	}
	public void setAd_copy(String ad_copy) {
		this.ad_copy = ad_copy;
	}
	public Timestamp getAd_update() {
		return ad_update;
	}
	public void setAd_update(Timestamp ad_update) {
		this.ad_update = ad_update;
	}
	

	

}

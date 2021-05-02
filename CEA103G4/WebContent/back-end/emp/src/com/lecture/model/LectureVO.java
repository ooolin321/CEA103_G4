package com.lecture.model;

import java.sql.Timestamp;

public class LectureVO implements java.io.Serializable{
	
	private String lc_id;
	private String lc_class_id;
	private String admin_id;
	private String lc_name;
	private String lc_place;
	private Timestamp lc_time;
	private Integer lc_hr;
	private Timestamp lc_deadline;
	private Timestamp lc_start_time;
	private Integer lc_peo_lim;
	private Integer lc_peo_up;
	private String lc_info;
	private byte[] lc_pic;
	private Integer lc_state;
	
	public String getLc_id() {
		return lc_id;
	}
	public void setLc_id(String lc_id) {
		this.lc_id =lc_id;
	}
	public String getLc_class_id() {
		return lc_class_id;
	}
	public void setLc_class_id(String lc_class_id) {
		this.lc_class_id = lc_class_id;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getLc_name() {
		return lc_name;
	}
	public void setLc_name(String lc_name) {
		this.lc_name = lc_name;
	}
	public String getLc_place() {
		return lc_place;
	}
	public void setLc_place(String lc_place) {
		this.lc_place = lc_place;
	}
	public Timestamp getLc_time() {
		return lc_time;
	}
	public void setLc_time(Timestamp lc_time) {
		this.lc_time = lc_time;
	}
	public Integer getLc_hr() {
		return lc_hr;
	}
	public void setLc_hr(Integer lc_hr) {
		this.lc_hr = lc_hr;
	}
	public Timestamp getLc_deadline() {
		return lc_deadline;
	}
	public void setLc_deadline(Timestamp lc_deadline) {
		this.lc_deadline = lc_deadline;
	}
	public Timestamp getLc_start_time() {
		return lc_start_time;
	}
	public void setLc_start_time(Timestamp lc_start_time) {
		this.lc_start_time = lc_start_time;
	}
	public Integer getLc_peo_lim() {
		return lc_peo_lim;
	}
	public void setLc_peo_lim(Integer lc_peo_lim) {
		this.lc_peo_lim = lc_peo_lim;
	}
	public Integer getLc_peo_up() {
		return lc_peo_up;
	}
	public void setLc_peo_up(Integer lc_peo_up) {
		this.lc_peo_up = lc_peo_up;
	}
	public String getLc_info() {
		return lc_info;
	}
	public void setLc_info(String lc_info) {
		this.lc_info = lc_info;
	}
	public byte[] getLc_pic() {
		return lc_pic;
	}
	public void setLc_pic(byte[] lc_pic) {
		this.lc_pic = lc_pic;
	}
	public Integer getLc_state() {
		return lc_state;
	}
	public void setLc_state(Integer lc_state) {
		this.lc_state = lc_state;
	}
	
}

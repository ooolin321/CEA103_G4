package com.bookclub.model;

import java.sql.Date;
import java.sql.Timestamp;

public class BookClubVO implements java.io.Serializable{
	private String bc_id;
	private String mem_id;
	private String bc_name;
	private String bc_place;
	private Timestamp bc_time_start;
	private Timestamp bc_time_end;
	private Integer bc_peo_upper_limit;
	private Integer bc_peo_lower_limit;
	private String bc_intro;
	private byte[] bc_cover_pic;
	private Integer bc_comfirm_peo;
	private Integer bc_status;
	private Date bc_init;
	private Date bc_deadline;
	private Timestamp bc_create_time;
	
	
	public String getBc_id() {
		return bc_id;
	}
	public void setBc_id(String bc_id) {
		this.bc_id = bc_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getBc_name() {
		return bc_name;
	}
	public void setBc_name(String bc_name) {
		this.bc_name = bc_name;
	}
	public String getBc_place() {
		return bc_place;
	}
	public void setBc_place(String bc_place) {
		this.bc_place = bc_place;
	}
	public Timestamp getBc_time_start() {
		return bc_time_start;
	}
	public void setBc_time_start(Timestamp bc_time_start) {
		this.bc_time_start = bc_time_start;
	}
	public Timestamp getBc_time_end() {
		return bc_time_end;
	}
	public void setBc_time_end(Timestamp bc_time_end) {
		this.bc_time_end = bc_time_end;
	}
	public Integer getBc_peo_upper_limit() {
		return bc_peo_upper_limit;
	}
	public void setBc_peo_upper_limit(Integer bc_peo_upper_limit) {
		this.bc_peo_upper_limit = bc_peo_upper_limit;
	}
	public Integer getBc_peo_lower_limit() {
		return bc_peo_lower_limit;
	}
	public void setBc_peo_lower_limit(Integer bc_peo_lower_limit) {
		this.bc_peo_lower_limit = bc_peo_lower_limit;
	}
	public String getBc_intro() {
		return bc_intro;
	}
	public void setBc_intro(String bc_intro) {
		this.bc_intro = bc_intro;
	}
	public byte[] getBc_cover_pic() {
		return bc_cover_pic;
	}
	public void setBc_cover_pic(byte[] bc_cover_pic) {
		this.bc_cover_pic = bc_cover_pic;
	}
	public Integer getBc_comfirm_peo() {
		return bc_comfirm_peo;
	}
	public void setBc_comfirm_peo(Integer bc_comfirm_peo) {
		this.bc_comfirm_peo = bc_comfirm_peo;
	}
	public Integer getBc_status() {
		return bc_status;
	}
	public void setBc_status(Integer bc_status) {
		this.bc_status = bc_status;
	}
	public Date getBc_init() {
		return bc_init;
	}
	public void setBc_init(Date bc_init) {
		this.bc_init = bc_init;
	}
	public Date getBc_deadline() {
		return bc_deadline;
	}
	public void setBc_deadline(Date bc_deadline) {
		this.bc_deadline = bc_deadline;
	}
	public Timestamp getBc_create_time() {
		return bc_create_time;
	}
	public void setBc_create_time(Timestamp bc_create_time) {
		this.bc_create_time = bc_create_time;
	}
	
	
	
	
	
	

}

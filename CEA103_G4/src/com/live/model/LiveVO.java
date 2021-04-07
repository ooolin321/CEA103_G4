package com.live.model;

import java.sql.Date;

public class LiveVO implements java.io.Serializable {
	private Integer live_id;
	private String live_type;
	private String live_name;
	private Date live_time;
	private String live_state;
	private String user_id;
	private Integer empno;

	public Integer getLive_id() {
		return live_id;
	}

	public void setLive_id(Integer live_id) {
		this.live_id = live_id;
	}

	public String getLive_type() {
		return live_type;
	}

	public void setLive_type(String live_type) {
		this.live_type = live_type;
	}

	public String getLive_name() {
		return live_name;
	}

	public void setLive_name(String live_name) {
		this.live_name = live_name;
	}

	public Date getLive_time() {
		return live_time;
	}

	public void setLive_time(Date live_time) {
		this.live_time = live_time;
	}

	public String getLive_state() {
		return live_state;
	}

	public void setLive_state(String live_state) {
		this.live_state = live_state;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

}

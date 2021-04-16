package com.product_report.model;

import java.sql.Date;

public class Product_ReportVO implements java.io.Serializable{
	private Integer pro_report_no;
	private String pro_report_content;
	private Integer product_no;
	private String user_id;
	private Date report_date;
	private Integer empno;
	private Integer proreport_state;
	
	public Integer getPro_report_no() {
		return pro_report_no;
	}
	public void setPro_report_no(Integer pro_report_no) {
		this.pro_report_no = pro_report_no;
	}
	public String getPro_report_content() {
		return pro_report_content;
	}
	public void setPro_report_content(String pro_report_content) {
		this.pro_report_content = pro_report_content;
	}
	public Integer getProduct_no() {
		return product_no;
	}
	public void setProduct_no(Integer product_no) {
		this.product_no = product_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getReport_date() {
		return report_date;
	}
	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getProreport_state() {
		return proreport_state;
	}
	public void setProreport_state(Integer proreport_state) {
		this.proreport_state = proreport_state;
	}
	
	
	
}

package com.emp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Random;


public class EmpVO implements Serializable{
	private Integer empno;
	private String ename;
	private String job;
	private String id;
	private Integer gender;
	private Date dob;
	private String addr;
	private String email;
	private Double sal;
	private Integer state;
	private Date hiredate;
	private String emp_pwd;
	private String genAuthCode;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public String getEmp_pwd() {
	
		return emp_pwd;
	}
	public void setEmp_pwd(String empPwd) {
		this.emp_pwd = empPwd;		
	}
	
	public String getGenAuthCode() {
		String empPwd = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//儲存數字0-9 和 大小寫字母
		StringBuffer sb = new StringBuffer(); //宣告一個StringBuffer物件sb 儲存 驗證碼
		for (int i = 0; i < 8; i++) {
			Random random = new Random();//建立一個新的隨機數生成器
			int index = random.nextInt(empPwd.length());//返回[0,string.length)範圍的int值    作用：儲存下標
			char ch = empPwd.charAt(index);//charAt() : 返回指定索引處的 char 值   ==》賦值給char字元物件ch
		 sb.append(ch);// append(char c) :將 char 引數的字串表示形式追加到此序列  ==》即將每次獲取的ch值作拼接
		}return sb.toString();		
	}
	
	public void setGenAuthCode(String genAuthCode) {
		this.genAuthCode = genAuthCode;
	}
	
	
}

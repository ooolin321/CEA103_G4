package com.fun.model;

import java.io.Serializable;

public class FunVO implements Serializable{
	private Integer funno;
	private String funName;
	
	public Integer getFunno() {
		return funno;
	}
	public void setFunno(Integer funno) {
		this.funno = funno;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	
}

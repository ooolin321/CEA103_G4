package com.mem.model;

import java.sql.Date;
import java.sql.Timestamp;

	public class MemVO implements java.io.Serializable{
		private String mem_id;
		private String mem_account;
		private String mem_password;
		private String mem_name;
		private String mem_email;
		private String mem_nickname;
		private Integer mem_sex;
		private Date mem_birth;
		private String mem_addr;
		private String mem_tel;
		private Double mem_bonus;
		private byte[] mem_pic;
		private Integer mem_iskol;
		private Double mem_exp;
		private Integer mem_status;
		private Timestamp mem_cretime;
		
		public String getMem_id() {
			return mem_id;
		}
		public void setMem_id(String mem_id) {
			this.mem_id = mem_id;
		}
		public String getMem_account() {
			return mem_account;
		}
		public void setMem_account(String mem_account) {
			this.mem_account = mem_account;
		}
		public String getMem_password() {
			return mem_password;
		}
		public void setMem_password(String mem_password) {
			this.mem_password = mem_password;
		}
		public String getMem_name() {
			return mem_name;
		}
		public void setMem_name(String mem_name) {
			this.mem_name = mem_name;
		}
		public String getMem_email() {
			return mem_email;
		}
		public void setMem_email(String mem_email) {
			this.mem_email = mem_email;
		}
		public String getMem_nickname() {
			return mem_nickname;
		}
		public void setMem_nickname(String mem_nickname) {
			this.mem_nickname = mem_nickname;
		}
		public Integer getMem_sex() {
			return mem_sex;
		}
		public void setMem_sex(Integer mem_sex) {
			this.mem_sex = mem_sex;
		}
		public Date getMem_birth() {
			return mem_birth;
		}
		public void setMem_birth(Date mem_birth) {
			this.mem_birth = mem_birth;
		}
		public String getMem_addr() {
			return mem_addr;
		}
		public void setMem_addr(String mem_addr) {
			this.mem_addr = mem_addr;
		}
		public String getMem_tel() {
			return mem_tel;
		}
		public void setMem_tel(String mem_tel) {
			this.mem_tel = mem_tel;
		}
		public Double getMem_bonus() {
			return mem_bonus;
		}
		public void setMem_bonus(Double mem_bonus) {
			this.mem_bonus = mem_bonus;
		}
		public byte[] getMem_pic() {
			return mem_pic;
		}
		public void setMem_pic(byte[] mem_pic) {
			this.mem_pic = mem_pic;
		}
		public Integer getMem_iskol() {
			return mem_iskol;
		}
		public void setMem_iskol(Integer mem_iskol) {
			this.mem_iskol = mem_iskol;
		}
		public Double getMem_exp() {
			return mem_exp;
		}
		public void setMem_exp(Double mem_exp) {
			this.mem_exp = mem_exp;
		}
		
		public Integer getMem_status() {
			return mem_status;
		}
		public void setMem_status(Integer mem_status) {
			this.mem_status = mem_status;
		}
		
		public Timestamp getMem_cretime() {
			return mem_cretime;
		}
		public void setMem_cretime(Timestamp mem_cretime) {
			this.mem_cretime = mem_cretime;
		}
	}


package com.mem.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class MemService {
	
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemDAO();
	}
	
	public MemVO addMem(String mem_account,String mem_password,String mem_name,
			String mem_email, String mem_nickname, Integer mem_sex, java.sql.Date mem_birth,
			String mem_addr, String mem_tel, byte[] mem_pic) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_account(mem_account);
		memVO.setMem_password(mem_password);
		memVO.setMem_name(mem_name);
		memVO.setMem_email(mem_email);
		memVO.setMem_nickname(mem_nickname);
		memVO.setMem_sex(mem_sex);
		memVO.setMem_birth(mem_birth);
		memVO.setMem_addr(mem_addr);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_pic(mem_pic);
		
		return dao.insert(memVO);
	}
	
	public MemVO updateMem(String mem_id, String mem_account,String mem_password,String mem_name,
			String mem_email, String mem_nickname, Integer mem_sex, java.sql.Date mem_birth,
			String mem_addr, String mem_tel,Double mem_bonus, byte[] mem_pic, Integer mem_iskol,
			Double mem_exp, Integer mem_status) {

		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		memVO.setMem_account(mem_account);
		memVO.setMem_password(mem_password);
		memVO.setMem_name(mem_name);
		memVO.setMem_email(mem_email);
		memVO.setMem_nickname(mem_nickname);
		memVO.setMem_sex(mem_sex);
		memVO.setMem_birth(mem_birth);
		memVO.setMem_addr(mem_addr);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_bonus(mem_bonus);
		memVO.setMem_pic(mem_pic);
		memVO.setMem_iskol(mem_iskol);
		memVO.setMem_exp(mem_exp);
		memVO.setMem_status(mem_status);
		dao.update(memVO);
		
		return memVO;
	}
	
	public void deleteMem(String mem_id) {
		dao.delete(mem_id);
	}
	
	public MemVO getOneMem(String mem_id) {
		return dao.findByPrimaryKey(mem_id);
	}
	
	public List<MemVO> getAll(){
		return dao.getAll();
	}
	
	//登入
	public MemVO signIn(String mem_account, String mem_password) {
		return dao.signIn(mem_account, mem_password);
	}
	
	//檢查帳號
	public boolean checkAcc(String mem_account) {
		return dao.checkAcc(mem_account);
	}
	//檢查EMAIL
	public boolean checkEmail(String mem_email) {
		return dao.checkEmail(mem_email);
	}
	public void updatePwd(String mem_id, String mem_password) {
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		memVO.setMem_password(mem_password);
		dao.updatePwd(memVO);
	}
	public String getMemIdbyMail(String mem_email) {
		
		return dao.getMemIdByMail(mem_email);
	}
	
	public List<MemVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}
	
	public void updateExp(MemVO memVO) {
		dao.update(memVO);
	}
	
	public void updateBonus(String mem_id, Double mem_bonus, Connection con) {
		dao.updateBonus(mem_id, mem_bonus, con);
	}
	
	public void updateStatusByAccount(String account) {
		dao.updateStatusByAccoutn(account);
	}
}

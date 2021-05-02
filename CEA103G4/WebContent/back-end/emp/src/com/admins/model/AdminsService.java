package com.admins.model;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class AdminsService {
	
	private AdminsDAO_interface dao;

	public AdminsService() {
		dao = new AdminsDAO();
	}

	public AdminsVO addAdmins(String admin_name, String admin_id_no, String admin_pswd,
			String admin_mobile, String admin_address, Date admin_dutydate ,
			Integer admin_jobstate, byte[] admin_pic, String admin_mail) {

		AdminsVO adminsVO = new AdminsVO();

		adminsVO.setAdmin_name(admin_name);
		adminsVO.setAdmin_id_no(admin_id_no);
		adminsVO.setAdmin_pswd(admin_pswd);
		adminsVO.setAdmin_mobile(admin_mobile);
		adminsVO.setAdmin_address(admin_address);
		adminsVO.setAdmin_dutydate(admin_dutydate);
		adminsVO.setAdmin_jobstate(admin_jobstate);
		adminsVO.setAdmin_pic(admin_pic);
		adminsVO.setAdmin_mail(admin_mail);
		dao.insert(adminsVO);
		
//		System.out.println("add OK"+ adminsVO.getAdmin_id());

		return adminsVO;
	}
	
	public AdminsVO updateAdmins(String admin_id, String admin_name, 
			String admin_mobile, String admin_address, Integer admin_jobstate
			, byte[] admin_pic, String admin_mail) {

		AdminsVO adminsVO = new AdminsVO();

		adminsVO.setAdmin_id(admin_id);
		adminsVO.setAdmin_name(admin_name);
		adminsVO.setAdmin_mobile(admin_mobile);
		adminsVO.setAdmin_address(admin_address);
		adminsVO.setAdmin_jobstate(admin_jobstate);
		adminsVO.setAdmin_pic(admin_pic);
		adminsVO.setAdmin_mail(admin_mail);
		
		dao.update(adminsVO);
		return adminsVO;				
	}

	public AdminsVO getOneAdmin(String admin_id) {
		return dao.findByPrimaryKey(admin_id);
	}
		
	public List<AdminsVO> getAll() {
		return dao.getAll();
	}
	
	public Optional<AdminsVO> getAdminPicByAdminId(String admin_id){
		return dao.findAdminPicByAdminId(admin_id);
	}
	
	public AdminsVO updatePswd(String admin_id, String admin_pswd) {
		AdminsVO adminsVO = new AdminsVO();
		
		adminsVO.setAdmin_id(admin_id);
		adminsVO.setAdmin_pswd(admin_pswd);
		
		dao.updatePswd(adminsVO);
		return adminsVO;
	}
}

package com.admins.model;

import java.util.*;

public interface AdminsDAO_interface {
	public Object insert(AdminsVO adminsVO);
	public void update(AdminsVO adminsVO);
	public AdminsVO findByPrimaryKey(String admin_id);
	public List<AdminsVO> getAll();
	Optional<AdminsVO> findAdminPicByAdminId(String admin_id);//show圖片
	
	//update password
	public void updatePswd(AdminsVO adminsVO);
}

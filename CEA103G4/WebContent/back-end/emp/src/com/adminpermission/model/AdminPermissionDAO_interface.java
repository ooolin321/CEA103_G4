package com.adminpermission.model;

import java.util.List;


public interface AdminPermissionDAO_interface {
	
	public void insert(AdminPermissionVO adminpermissionVO);
	public void delete(String admin_id);
	public List<AdminPermissionVO> findByPrimaryKey(String admin_id);		
	public List<AdminPermissionVO> getAll();    
}

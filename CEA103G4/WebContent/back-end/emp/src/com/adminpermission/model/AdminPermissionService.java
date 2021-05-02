package com.adminpermission.model;

import java.util.*;

public class AdminPermissionService {
	private AdminPermissionDAO_interface dao;

	public AdminPermissionService() {
		dao = new AdminPermissionDAO();
	}

	public AdminPermissionVO addAdminPermission(String admin_id, String per_id) {

		AdminPermissionVO adminpermissionVO = new AdminPermissionVO();

		adminpermissionVO.setAdmin_id(admin_id);
		adminpermissionVO.setPer_id(per_id);

		dao.insert(adminpermissionVO);

		return adminpermissionVO;
	}

	public void deleteAdminPermission(String admin_id) {
		dao.delete(admin_id);
	}

//一個員工只能取到多權限編號
	public List<AdminPermissionVO> getOneAdminPermission(String admin_id) {
		return dao.findByPrimaryKey(admin_id);
	}

	public List<AdminPermissionVO> getAll() {
		return dao.getAll();
	}
}

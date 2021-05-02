package com.permissiondelimit.model;

import java.util.List;

public class PermissionDelimitService {
	
	private PermissionDelimitDAO_interface dao;

	public PermissionDelimitService() {
		dao = new PermissionDelimitDAO();
	}

	public PermissionDelimitVO addPermissionDelimit(String per_name) {

		PermissionDelimitVO permissiondelimitVO = new PermissionDelimitVO();

		permissiondelimitVO.setPer_name(per_name);
		dao.insert(permissiondelimitVO);

		return permissiondelimitVO;
	}

	public PermissionDelimitVO updatePermissionDelimit(String per_id, String per_name) {

		PermissionDelimitVO permissiondelimitVO = new PermissionDelimitVO();

		permissiondelimitVO.setPer_id(per_id);
		permissiondelimitVO.setPer_name(per_name);		
		dao.update(permissiondelimitVO);

		return permissiondelimitVO;
	}

	public void deletePermissionDelimit(String per_id) {
		dao.delete(per_id);
	}

	public PermissionDelimitVO getOnePermissionDelimit(String per_id) {
		return dao.findByPrimaryKey(per_id);
	}

	public List<PermissionDelimitVO> getAll() {
		return dao.getAll();
	}
	
	public PermissionDelimitVO getOneUri(String per_uri) {
		return dao.findByUri(per_uri);
	}

}
